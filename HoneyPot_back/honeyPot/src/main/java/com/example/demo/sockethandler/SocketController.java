package com.example.demo.sockethandler;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.example.demo.chat.ChatDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SocketController {
	
	
		private final SimpMessageSendingOperations simpMessageSendingOperations;
	
		// /receive를 메세지를 받을 endpoint로 설정
		// 1)클라이언트에서 /pub/receive로 메세지 발행
		@MessageMapping("/receive")
		public void SocketHandler(ChatDto dto) {
			
			// 2)/sub/channel/boardNum에 구독중인 클라이언트에게 메시지를 보냄
			simpMessageSendingOperations.convertAndSend("/sub/channel/" + dto.getBoardNum(), dto);
			System.out.println(" SocketHandler 요청옴");
			
		}
		
		
		
		
		
		
		
		
		
		// /send로 메세지를 반환
//		@SendTo("/sub")
		
		
		// /receive에서 메세지를 받고, /send로 메세지를 보내줌
//		public Chat SocketHandler(ChatDto dto) {
//			System.out.println(" SocketHandler 요청옴");
//			Chat result = new Chat(dto.getChatNum(), dto.getBoardNum(), dto.getIsFromSender(), dto.getContent(), dto.getTime());
//			return result;
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//
//List<WebSocketSession> sessions = new ArrayList<>();
//
//	
//	//소켓 연결 확인
//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
//		System.out.println("afterConnectionEstablished:" + session);
//		sessions.add(session);
//		
//	
//	}
//
//	// 메세지 보냈을 때
//	@Override
//	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//		System.out.println("handleTextMessage:" + session + " : " + message);
//		String senderId = session.getId();
//		
//		for (WebSocketSession sess: sessions) {
//			sess.sendMessage(new TextMessage(senderId + ": " + message.getPayload()));
//		}
//	}	
//
//	// 소켓 종료 확인
//	@Override
//	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		System.out.println("afterConnectionEstablished:" + session + ":" + status);
//	}	

}
