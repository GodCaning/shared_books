package com.xust.wtc.configurer;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Spirit on 2017/12/7.
 */
@Configuration
public class ElasticSearchConfig {

    public void a() {

    }

    @Bean
    public Client client() {
        Settings settings = Settings.settingsBuilder()
                .put("client.transport.sniff",true)
                .put("cluster.name", "wtc-application").build();
        Client client = null;
        try {
             client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            return client;
        }
    }

}
