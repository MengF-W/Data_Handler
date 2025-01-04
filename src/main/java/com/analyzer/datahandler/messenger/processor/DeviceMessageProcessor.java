package com.analyzer.datahandler.messenger.processor;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.analyzer.datahandler.database.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DeviceMessageProcessor implements MqttMessageProcessor{

    private final String DATABASE_URL = "http://localhost:8080/db/createContent";

    @Autowired
    private  DeviceRepository deviceRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Async
    public void processMessage(String topic, MqttMessage message) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<String>(new String(message.getPayload()), headers);

        String responseResult =
                restTemplate.postForObject(DATABASE_URL, request, String.class);

        System.out.println(responseResult);
    }

}
