package com.analyzer.datahandler.database.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "com.analyzer.datahandler")
@EnableElasticsearchRepositories(basePackages = "com.analyzer.datahandler.database.repository")
public class ElasticConfiguration
{
    @Autowired
    private Environment environment;

    @Value("${running.mode}")
    private String runningMode;

    @Bean
    public RestHighLevelClient initElasticsearchClient() {

        final String DATABASE_IP = environment.getProperty("elasticsearch.ip");

        final String DATABASE_PORT = environment.getProperty("elasticsearch.port");

        HttpHeaders compatibilityHeaders = new HttpHeaders();
        compatibilityHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
        compatibilityHeaders.add("Content-Type", "application/vnd.elasticsearch+json;" + "compatible-with=7");

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(DATABASE_IP+":"+DATABASE_PORT)
                .withDefaultHeaders(compatibilityHeaders)
                .build();

        RestHighLevelClient restHighLevelClient = RestClients.create(clientConfiguration).rest();

        ElasticsearchClient client = new ElasticsearchClient(new RestClientTransport(
                restHighLevelClient.getLowLevelClient(), new JacksonJsonpMapper()));

        if (!runningMode.equals("Testing"))
        {
            try {
                if(client.ping().value()){
                    System.out.println("Elastic is connected");
                }
            } catch (IOException e) {
                System.out.println("Elastic is not connected");
                throw new RuntimeException(e);
            }

        }
        return restHighLevelClient;
    }

}
