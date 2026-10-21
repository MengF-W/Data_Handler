package com.analyzer.datahandler.database.controller;


import com.analyzer.datahandler.database.elastic.controller.DatabaseElasticController;
import com.analyzer.datahandler.database.elastic.model.Device;
import com.analyzer.datahandler.database.elastic.repository.DeviceElasticRepository;
import com.analyzer.datahandler.database.utilities.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DatabaseElasticController.class)
public class DatabaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceElasticRepository mockDeviceRepository;

    @Test
    public void testCreateData() throws Exception {

        String someDeviceMessage =  new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create().toJson(new Device(123, "someDeviceName", "someDeviceType", "someDeviceMessageContent", new byte[1],LocalDateTime.now()));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/db/elastic/createContent")
                        .content(someDeviceMessage)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(DatabaseElasticController.RESPONSE_CREATED_MESSAGE));


    }
}
