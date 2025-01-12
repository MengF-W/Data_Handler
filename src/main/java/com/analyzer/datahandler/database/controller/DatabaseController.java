package com.analyzer.datahandler.database.controller;

import com.analyzer.datahandler.database.model.Device;
import com.analyzer.datahandler.database.repository.DeviceRepository;
import com.analyzer.datahandler.database.utilities.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/db")
public class DatabaseController {

    @Autowired
    DeviceRepository deviceRepository;

    public static final String RESPONSE_CREATED_MESSAGE = "Device data is created in database";


    @PostMapping(path= "/createContent" ,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createData(@RequestBody String content) {

        Device device = JsonParser.getInstance().deserializeJson(content,Device.class);
        deviceRepository.save(device);
        return new ResponseEntity<>(RESPONSE_CREATED_MESSAGE, HttpStatus.CREATED);

    }


}
