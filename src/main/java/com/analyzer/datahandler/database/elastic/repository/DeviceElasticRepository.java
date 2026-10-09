package com.analyzer.datahandler.database.elastic.repository;


import com.analyzer.datahandler.database.elastic.model.Device;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface DeviceElasticRepository extends ElasticsearchRepository<Device, String> {

    List<Device> findByName(String deviceName);

    List<Device> findAll();

}