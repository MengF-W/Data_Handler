package com.analyzer.datahandler.database.postgres.repository;


import com.analyzer.datahandler.database.postgres.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DevicePostgresRepository extends JpaRepository<Device, String> {

    List<Device> findByName(String deviceName);

    List<Device> findAll();

}