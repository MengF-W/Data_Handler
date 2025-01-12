package com.analyzer.datahandler.database.controller;


import com.analyzer.datahandler.database.model.Device;
import com.analyzer.datahandler.database.repository.DeviceRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DatabaseController.class)
public class DatabaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceRepository mockDeviceRepository;

    @Test
    public void testCreateData() throws Exception {

        String someDeviceMessage = new Gson().toJson(new Device("someDeviceId", "someDeviceName", "someDeviceType", "someDeviceMessageContent"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/db/createContent")
                        .content(someDeviceMessage)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(DatabaseController.RESPONSE_CREATED_MESSAGE));


    }
}
