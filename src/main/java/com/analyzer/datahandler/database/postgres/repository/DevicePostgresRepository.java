package com.analyzer.datahandler.database.postgres.repository;


import com.analyzer.datahandler.database.postgres.model.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nonnull;
import java.util.List;


public interface DevicePostgresRepository extends JpaRepository<Device, Long> {

    List<Device> findByName(String deviceName);

    @Nonnull Page<Device> findAll(@Nonnull Pageable pageable);

    @Query("select d from Device d where d.id > :lastId order by d.id ASC")
    List<Device> findAllKeySetPagination(@Param("lastId")Long lastId, Pageable pageable);


}