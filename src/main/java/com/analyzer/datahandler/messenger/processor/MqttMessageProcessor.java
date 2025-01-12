package com.analyzer.datahandler.messenger.processor;

import org.eclipse.paho.client.mqttv3.*;

public interface MqttMessageProcessor{
    <T> T processMessage(String topic, MqttMessage message) throws Exception;
}
