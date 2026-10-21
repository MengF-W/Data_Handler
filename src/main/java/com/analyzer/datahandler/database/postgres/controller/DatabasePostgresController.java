package com.analyzer.datahandler.database.postgres.controller;

import com.analyzer.datahandler.database.postgres.model.Device;
import com.analyzer.datahandler.database.postgres.repository.DevicePostgresRepository;
import com.analyzer.datahandler.database.utilities.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/db/postgres")
public class DatabasePostgresController {

    @Autowired
    DevicePostgresRepository deviceRepository;

    public static final String RESPONSE_CREATED_MESSAGE = "Device data is created in database";


    @PostMapping(path= "/createContent" ,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createData(@RequestBody String content) {

        Device device = JsonParser.getInstance().deserializeJson(content, Device.class);
        deviceRepository.save(device);
        return new ResponseEntity<>(RESPONSE_CREATED_MESSAGE, HttpStatus.CREATED);

    }

    @GetMapping(path= "/getAllData" ,
            params = { "pageNumber", "pageSize" },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Device> getAllData(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("timeStamp").descending());

        return deviceRepository.findAll(pageable);

    }

}
