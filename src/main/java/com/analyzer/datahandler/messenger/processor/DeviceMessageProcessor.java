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
    private RestTemplate restTemplate;

    @Override
    @Async
    public String processMessage(String topic, MqttMessage message){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<String>(new String(message.getPayload()), httpHeaders);

        String responseResult =
                restTemplate.postForObject(DATABASE_URL, request, String.class);

        System.out.println(responseResult);

        return responseResult;
    }

}
