package com.analyzer.datahandler.messenger.config;

import com.analyzer.datahandler.database.elastic.configuration.ElasticConfiguration;
import org.eclipse.paho.client.mqttv3.*;
import com.analyzer.datahandler.messenger.processor.DeviceMessageProcessor;
import com.analyzer.datahandler.messenger.processor.MqttMessageProcessor;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@ComponentScan(basePackages = "com.analyzer.datahandler")
public class MqttConfig {

    Logger logger = LoggerFactory.getLogger(MqttConfig.class);

    private final String DEVICE_RECEIVE_TOPIC = "/device/receive";

    private MqttConnectOptions options;

    @Value("${running.mode}")
    private String runningMode;

    @Autowired
    private DeviceMessageProcessor deviceMessageProcessor;

    @Autowired
    private Environment environment;


    @Bean
    public IMqttClient initMqttClient() {

        return initConnection();
    }

    private IMqttClient initConnection(){

        final String MQTT_SERVER_ADDRES= environment.getProperty("mqtt.server.address");
        final String MQTT_PUBLISHER_ID = UUID.randomUUID().toString();

        IMqttClient iMqttClient = null;

        try {

            if(Boolean.parseBoolean(environment.getProperty("mqtt.filePersistence")))
            {
                iMqttClient = new MqttClient(MQTT_SERVER_ADDRES, MQTT_PUBLISHER_ID);
            }
            else
            {

                iMqttClient = new MqttClient(MQTT_SERVER_ADDRES, MQTT_PUBLISHER_ID,new MemoryPersistence());
            }

            this.options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(60);
            if (!runningMode.equals("Testing"))
            {
                if(iMqttClient.connectWithResult(options).getClient() != null && iMqttClient.isConnected())
                {
                    logger.info("MQTT is connected");
                    setTopic(iMqttClient);
                }
                else
                {
                    initConnection();
                }
            }

        } catch (MqttException e) {
            logger.debug("MQTT is not connected. " + e.getMessage());
            initConnection();
        }

        return iMqttClient;
    }

    private void setTopic(IMqttClient iMqttClient) throws MqttException {

        Map<String, MqttMessageProcessor> dispatchMap = new HashMap<>();
        dispatchMap.put(DEVICE_RECEIVE_TOPIC, deviceMessageProcessor);

        iMqttClient.subscribe(DEVICE_RECEIVE_TOPIC);

        iMqttClient.setCallback(new MqttCallback() {

            public void messageArrived(String topic, MqttMessage message) throws Exception {

                logger.info("Message Arrived" );
                logger.info("Topic: " + topic );
                logger.info("Message: " + new String(message.getPayload()));
                logger.info("QoS: " + message.getQos());

                dispatchMap.get(topic).processMessage(topic, message);
            }

            public void connectionLost(Throwable cause) {

                logger.debug("MQTT connection lost. " + cause.getMessage());
                logger.debug("MQTT is reconnecting. ");
                initConnection();
                try {

                    while(!iMqttClient.connectWithResult(options).getClient().isConnected())
                    {
                        initConnection();
                    }

                }catch (MqttException e) {

                    logger.debug("MQTT connection failed to reconnect " + cause.getMessage());
                    connectionLost(e);
                }

            }

            public void deliveryComplete(IMqttDeliveryToken token) {
            }

        });

    }
}
