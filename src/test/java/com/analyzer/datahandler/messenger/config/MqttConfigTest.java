package com.analyzer.datahandler.messenger.config;

import com.analyzer.datahandler.database.controller.DatabaseController;
import com.analyzer.datahandler.database.repository.DeviceRepository;
import com.analyzer.datahandler.messenger.processor.DeviceMessageProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqttConfig.class)
@TestPropertySource(properties = {"running.mode=Testing"})
@MockBeans({@MockBean(DeviceRepository.class)})
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
