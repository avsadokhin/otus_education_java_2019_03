package ru.otus.hw15.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, ApplicationListener<BrokerAvailabilityEvent> {
    private static Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);


    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
        logger.info("Going to start relay broker on host {}");
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/web-socket-endpoint").withSockJS();
    }

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent e) {
        logger.info("broker available: " + e.isBrokerAvailable());
    }
}
