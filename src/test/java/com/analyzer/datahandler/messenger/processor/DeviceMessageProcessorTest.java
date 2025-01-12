package com.analyzer.datahandler.messenger.processor;

import com.analyzer.datahandler.database.controller.DatabaseController;
import com.analyzer.datahandler.database.model.Device;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceMessageProcessor.class)
public class DeviceMessageProcessorTest {

    @Autowired
    private DeviceMessageProcessor deviceMessageProcessor;

    @MockBean
    private RestTemplate mockRestTemplate;

    private MqttMessage someMqttMessage;

    private HttpHeaders testHttpHeaders;

    private HttpEntity<String> testRequest;

    private String testMqttTopic;

    private final String DATABASE_URL = "http://localhost:8080/db/createContent";


    public DeviceMessageProcessorTest() {
    }

    @Before
    public void setUp() throws Exception
    {
        String someDeviceMessage = new Gson().toJson(new Device("someDeviceId", "someDeviceName", "someDeviceType", "someDeviceMessageContent"));
        someMqttMessage = new MqttMessage(someDeviceMessage.getBytes());
        testHttpHeaders = new HttpHeaders();
        testHttpHeaders.setContentType(MediaType.APPLICATION_JSON);
        testRequest = new HttpEntity<String>(someDeviceMessage, testHttpHeaders);
    }

    @Test
    public void testProcessMessage() {

        testMqttTopic = "/device/receive";

        when(mockRestTemplate.postForObject(
                eq(DATABASE_URL),
                eq(testRequest),
                eq(String.class)
        )).thenReturn(DatabaseController.RESPONSE_CREATED_MESSAGE);

        assertEquals(DatabaseController.RESPONSE_CREATED_MESSAGE, deviceMessageProcessor.processMessage(testMqttTopic,someMqttMessage));

        verify(mockRestTemplate, times(1)).postForObject(
                eq(DATABASE_URL),
                eq(testRequest),
                eq(String.class));


    }

}

