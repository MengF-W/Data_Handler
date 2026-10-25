package com.analyzer.datahandler.database.configuration;

import com.analyzer.datahandler.database.elastic.configuration.ElasticConfiguration;
import com.analyzer.datahandler.database.elastic.controller.DatabaseElasticController;
import com.analyzer.datahandler.database.elastic.repository.DeviceElasticRepository;
import com.analyzer.datahandler.database.postgres.controller.DatabasePostgresController;
import com.analyzer.datahandler.database.postgres.repository.DevicePostgresRepository;
import com.analyzer.datahandler.messenger.config.MqttConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticConfiguration.class)
@TestPropertySource(properties = {"running.mode=Testing"})
@MockBeans({@MockBean(DatabaseElasticController.class), @MockBean(DeviceElasticRepository.class),@MockBean(MqttConfig.class),@MockBean(DatabasePostgresController.class), @MockBean(DevicePostgresRepository.class)})
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
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
