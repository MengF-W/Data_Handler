package com.analyzer.datahandler.messenger.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqttConfig.class)
@TestPropertySource(properties = {"running.mode=Testing"})
public class MqttConfigTest {

    @Autowired
    MqttConfig mqttConfig;

    @Autowired
    Environment environment;
    @Test
    public void testMqttConfigurationIsValid() {
        assertNotNull(mqttConfig);
        assertTrue(mqttConfig.initMqttClient().getServerURI().contains(environment.getProperty("mqtt.ip")));
        assertTrue(mqttConfig.initMqttClient().getServerURI().contains(environment.getProperty("mqtt.port")));

    }
}
