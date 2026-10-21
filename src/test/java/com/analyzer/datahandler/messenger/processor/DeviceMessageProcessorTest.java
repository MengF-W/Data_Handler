package com.analyzer.datahandler.messenger.processor;

import com.analyzer.datahandler.database.elastic.controller.DatabaseElasticController;
import com.analyzer.datahandler.database.elastic.model.Device;
import com.analyzer.datahandler.database.utilities.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceMessageProcessor.class)
public class DeviceMessageProcessorTest {

    @Autowired
    private DeviceMessageProcessor deviceMessageProcessor;

    @Autowired
    private Environment environment;

    @MockBean
    private RestTemplate mockRestTemplate;

    private MqttMessage someMqttMessage;

    private HttpHeaders testHttpHeaders;

    private HttpEntity<String> testRequest;

    private String testMqttTopic;

    private final String DATABASE_URL = "http://localhost:8080/db/postgres/createContent";


    public DeviceMessageProcessorTest() {
    }

    @Before
    public void setUp() throws Exception
    {
        String someDeviceMessage =  new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create().toJson(new Device(123, "someDeviceName", "someDeviceType", "someDeviceMessageContent", new byte[1], LocalDateTime.now()));
        someMqttMessage = new MqttMessage(compressPayload(someDeviceMessage.getBytes()));
        testHttpHeaders = new HttpHeaders();
        testHttpHeaders.setContentType(MediaType.APPLICATION_JSON);
        testRequest = new HttpEntity<String>(someDeviceMessage, testHttpHeaders);
    }

    @Test
    public void testProcessMessage() throws DataFormatException {

        testMqttTopic = "/device/receive";

        when(mockRestTemplate.postForObject(
                eq(DATABASE_URL),
                eq(testRequest),
                eq(String.class)
        )).thenReturn(DatabaseElasticController.RESPONSE_CREATED_MESSAGE);

        assertEquals(DatabaseElasticController.RESPONSE_CREATED_MESSAGE, deviceMessageProcessor.processMessage(testMqttTopic,someMqttMessage));

        verify(mockRestTemplate, times(1)).postForObject(
                eq(DATABASE_URL),
                eq(testRequest),
                eq(String.class));


    }

    private byte[] compressPayload(byte[] payload){

        Deflater deflater = new Deflater();
        deflater.setInput(payload);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int compressedSize = deflater.deflate(buffer);
            outputStream.write(buffer, 0, compressedSize);
        }

        return outputStream.toByteArray();
    }

}

