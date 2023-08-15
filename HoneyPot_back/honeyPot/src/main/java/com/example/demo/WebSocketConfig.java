package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.demo.sockethandler.EchoHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub

//			registry.addHandler(new ReplyEchoHandler(), "/replyEcho").setAllowedOrigins("*"); //pure websocket
			
			// sockJS 방식
			registry.addHandler(new EchoHandler(), "/replyEcho").setAllowedOrigins("*").withSockJS();
	}
	

}
