package com.analyzer.datahandler.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.elasticsearch.core.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;

@Document(indexName = "devices")
public class Device {
    public Device(String id, String name, String deviceType, String messageContent, LocalDateTime timeStamp) {
        this.id = id;
        this.name = name;
        this.deviceType = deviceType;
        this.messageContent = messageContent;
        this.timeStamp = timeStamp;
    }

    @Id
    private String id;

    private String name;
    private String deviceType;

    private String messageContent;

//    @Field(name = "@timestamp", type = FieldType.Date,pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//    private Date timeStamp;
    //    @Field(name = "@timestamp", format = DateFormat.custom,pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
@Field(name = "@timestamp", type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
@JsonDeserialize(using = LocalDateDeserializer.class)
@JsonFormat(pattern="dd/MM/yyyy hh:mm:ss")
    private LocalDateTime timeStamp;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }



}
