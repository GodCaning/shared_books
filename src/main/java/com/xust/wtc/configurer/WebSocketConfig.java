package com.xust.wtc.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by Spirit on 2017/12/3.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/wtc").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableStompBrokerRelay("/topic", "/queue")
//                    .setRelayHost("")
//                    .setRelayPort(62623)
//                    .setClientLogin("")
//                    .setClientPasscode("");
        //这句表示在topic和user这两个域上可以向客户端发消息；
        registry.enableSimpleBroker("/topic", "/user");
        registry.setApplicationDestinationPrefixes("/app");
//        registry.setUserDestinationPrefix("/user/");
//keytool -genkey -alias tomcat
// -storetype PKCS12 -keyalg RSA -keysize 2048  -keystore keystore.p12 -validity 3650
    }
}
