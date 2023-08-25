package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		System.out.println("소켓 연결 시도 : " + registry);
       registry.addEndpoint("/room").setAllowedOriginPatterns("*").withSockJS();

    }
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
		
	   //enableSimpleBroker : 내장 브로커 사용. 메시지를 받고 구독자들에게 전달해줌
	   registry.enableSimpleBroker("/sub");
	   
	   //setApplicationDestinationPrefixes : 바로 브로커로 가는 것이 아니라 메시지의 어떤 처리나 가공이 필요할 때 핸들러를 거쳐감
	   registry.setApplicationDestinationPrefixes("/pub");
        
    }
	

}
