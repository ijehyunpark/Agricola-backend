package com.semoss.agricola.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 웹 소켓 연결을 구성하기 위한 메소드르 구현하고 제공한다. <br>
 * {@link WebSocketConfig}(단순 WebSocket 설정 인터페이스) 에 추가적으로 STOMP을 사용하여 메시지 브로커를 포함한 기능을 제공한다.
 */
@Configuration
@EnableWebSocketMessageBroker //WebSocket을 활성화 한다.( @EnableWebSocket를 포함하여 메시지 브로커를 설정하기 위한 추가적인 구성을 제공한다.)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*"); //.withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
