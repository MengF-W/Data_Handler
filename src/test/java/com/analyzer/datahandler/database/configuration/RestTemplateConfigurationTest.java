package com.analyzer.datahandler.database.configuration;

import static org.junit.Assert.*;

import com.analyzer.datahandler.database.configuration.RestTemplateConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestTemplateConfiguration.class)
public class RestTemplateConfigurationTest {

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @Test
    public void testRestTemplateConfigurationBeanIsCreated() {
        assertNotNull(restTemplateConfiguration);
    }

}
