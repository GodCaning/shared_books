//package com.xust.wtc.configurer;
//
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PreDestroy;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
///**
// * 当ES服务器监听使用内网服务器IP而访问使用外网IP时，
// * 不要使用client.transport.sniff为true，
// * 在自动发现时会使用内网IP进行通信，
// * 导致无法连接到ES服务器，
// * 而直接使用addTransportAddress方法进行指定ES服务器。
// *
// * Created by Spirit on 2017/12/7.
// */
//@Configuration
//public class ElasticSearchConfig {
//
//    @Bean
//    public Client client() {
//        Settings settings = Settings.settingsBuilder()
////                .put("client.transport.sniff",true)
//                .put("cluster.name", "wtc-application").build();
//        Client client = null;
//        try {
//             client = TransportClient.builder().settings(settings).build()
//                    .addTransportAddress(new InetSocketTransportAddress(
//                            InetAddress.getByName("193.112.4.174"), 9300));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } finally {
//            return client;
//        }
//    }
//
//}
