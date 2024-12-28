package com.analyzer.datahandler.messenger.processor;

import org.eclipse.paho.client.mqttv3.*;

public interface MqttMessageProcessor{
    void processMessage(String topic, MqttMessage message) throws Exception;
}
