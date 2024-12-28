package com.analyzer.datahandler.messenger.config;

import org.eclipse.paho.client.mqttv3.*;
import com.analyzer.datahandler.messenger.processor.DeviceMessageProcessor;
import com.analyzer.datahandler.messenger.processor.MqttMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@ComponentScan(basePackages = "com.analyzer.datahandler")
public class MqttConfig {
    private final String MQTT_BROKER_IP = "localhost";

    private final String MQTT_BROKER_PORT = "1883";
    private final String MQTT_SERVER_ADDRES= "tcp://"+MQTT_BROKER_IP+":"+MQTT_BROKER_PORT;

    private final String MQTT_PUBLISHER_ID = UUID.randomUUID().toString();

    private final String DEVICE_RECEIVE_TOPIC = "/device/receive";

    @Autowired
    private DeviceMessageProcessor deviceMessageProcessor;


    @Bean
    public IMqttClient initMqttClient() {

        IMqttClient iMqttClient = initConnection();

        return iMqttClient;
    }

    private IMqttClient initConnection(){

        IMqttClient iMqttClient = null;

        try {
            iMqttClient = new MqttClient(MQTT_SERVER_ADDRES, MQTT_PUBLISHER_ID);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            if(iMqttClient.connectWithResult(options).getClient().isConnected())
            {
                System.out.println("MQTT is connected");
                setTopic(iMqttClient);
            }

        } catch (MqttException e) {
            e.printStackTrace();
        }

        return iMqttClient;
    }

    private void setTopic(IMqttClient iMqttClient) throws MqttException {

        Map<String, MqttMessageProcessor> dispatchMap = new HashMap<>();
        dispatchMap.put(DEVICE_RECEIVE_TOPIC, deviceMessageProcessor);

        iMqttClient.subscribe(DEVICE_RECEIVE_TOPIC);

        iMqttClient.setCallback(new MqttCallback() {

            public void messageArrived(String topic, MqttMessage message) throws Exception {

                System.out.println("Message Arrived" );
                System.out.println("Topic: " + topic );
                System.out.println("Message: " + new String(message.getPayload()));
                System.out.println("QoS: " + message.getQos());

                dispatchMap.get(topic).processMessage(topic, message);
            }

            public void connectionLost(Throwable cause) {

                System.out.println("MQTT connection is disconnected. " + cause.getMessage());

            }

            public void deliveryComplete(IMqttDeliveryToken token) {
            }

        });

    }
}
