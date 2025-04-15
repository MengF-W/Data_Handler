package com.analyzer.datahandler.database.repository;


import com.analyzer.datahandler.database.model.Device;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface DeviceRepository extends ElasticsearchRepository<Device, String> {

    List<Device> findByName(String deviceName);

    List<Device> findAll();

}