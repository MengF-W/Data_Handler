package com.analyzer.datahandler.database.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.analyzer.datahandler.database.configuration.ElasticConfiguration;
import com.analyzer.datahandler.database.controller.DatabaseController;
import com.analyzer.datahandler.database.repository.DeviceRepository;
import com.analyzer.datahandler.messenger.config.MqttConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticConfiguration.class)
@TestPropertySource(properties = {"running.mode=Testing"})
@MockBeans({@MockBean(DatabaseController.class), @MockBean(DeviceRepository.class),@MockBean(MqttConfig.class)})
public class ElasticConfigurationTest {

    @Autowired
    private Environment environment;

    @Autowired
    private ElasticConfiguration elasticConfigurationlasticConfiguration;

    @Test
    public void testElasticConfigurationIsValid() {

        assertNotNull(elasticConfigurationlasticConfiguration);
        assertEquals(environment.getProperty("elasticsearch.ip"),elasticConfigurationlasticConfiguration.initElasticsearchClient().getLowLevelClient().getNodes().get(0).getHost().getHostName());
        assertEquals(Integer.parseInt(environment.getProperty("elasticsearch.port")),elasticConfigurationlasticConfiguration.initElasticsearchClient().getLowLevelClient().getNodes().get(0).getHost().getPort());

    }
}
