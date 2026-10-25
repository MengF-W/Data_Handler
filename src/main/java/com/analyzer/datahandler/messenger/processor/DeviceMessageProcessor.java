package com.analyzer.datahandler.messenger.processor;

import com.analyzer.datahandler.messenger.config.MqttConfig;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Component
public class DeviceMessageProcessor implements MqttMessageProcessor{

    Logger logger = LoggerFactory.getLogger(DeviceMessageProcessor.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Override
    @Async
    public String processMessage(String topic, MqttMessage message) throws DataFormatException {

        final String DATABASE_URL = environment.getProperty("app.host") + "/db/postgres/createContent";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<String>(new String(decompressPayload(message.getPayload())), httpHeaders);

        String responseResult =
                restTemplate.postForObject(DATABASE_URL, request, String.class);

        logger.info("Message process response result: " + responseResult);

        return responseResult;
    }

    public static byte[] decompressPayload(byte[] input) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(input);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int decompressedSize = inflater.inflate(buffer);
            byteArrayOutputStream.write(buffer, 0, decompressedSize);
        }

        return byteArrayOutputStream.toByteArray();
    }

}
