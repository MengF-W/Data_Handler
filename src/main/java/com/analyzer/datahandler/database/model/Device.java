package com.analyzer.datahandler.database.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "devices")
public class Device {
    public Device(String id, String name, String deviceType, String messageContent) {
        this.id = id;
        this.name = name;
        this.deviceType = deviceType;
        this.messageContent = messageContent;
    }

    @Id
    private String id;

    private String name;
    private String deviceType;

    private String messageContent;

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





}
