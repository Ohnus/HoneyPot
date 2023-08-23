package com.example.demo.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.chatCheck.ChatCheckDto;
import com.example.demo.chatCheck.ChatCheckService;

@RestController
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatService service;
	
	@Autowired
	private ChatCheckService CCservice;
	
	
	
	
	//채팅 작성 ( 테스트 완 )
	@PostMapping("")
	public Map add(ChatDto dto) {
		System.out.println("요청옴");
		Map map = new HashMap();
		boolean flag = true;
		ChatDto dto2 = null;
		
		try{
		
		// 채팅 한 개 chat테이블에 추가
		dto2 = service.add(dto); 
		System.out.println("getIsFromSender : " + dto.getIsFromSender());
		System.out.println("getIsFromSender.getUserNum() : " + dto.getIsFromSender().getUserNum());
		System.out.println("getIsFromSender.getNickname() : " + dto.getIsFromSender().getNickname());
		System.out.println("dto2.getChatNum : " + dto2.getChatNum());
		
		//채팅 한 개에 대해서 각 채팅방 멤버 별 인원수만큼 chatcheck(읽음 여부 확인하기 위한)테이블에 튜플 추가
		ArrayList<ChatCheckDto> list2 = CCservice.add(dto.getBoardNum(),dto2.getChatNum(),dto.getIsFromSender().getUserNum()); 
		System.out.println("list2 : " + list2);
		}catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		map.put("dto", dto2);
		System.out.println("flag : " + flag);
		System.out.println("채팅보내기 : " + dto2);
		return map;
	}
	
	//각 채팅방 별 대화 내용 보여주기 ( 테스트 완 )
	@GetMapping("/{loginId}/{boardNum}")
	public Map getchat(@PathVariable("loginId") String loginId, @PathVariable("boardNum") int boardNum) {
		
		System.out.println("각 채팅방 별 대화 내용 보여주기 :   " + " loginId : " + loginId + " baordNum : " +  boardNum);
		// 채팅방 클릭 했을 때 모두 읽음 처리
		CCservice.changeRead(boardNum, loginId);
		
		ArrayList<ChatDto> list = service.getChat(boardNum);
		Map map = new HashMap();
		map.put("chat", list);
		System.out.println("chat list : " + list);
		System.out.println("대화 내용 보여주기");
		
		
		return map;
	}
}
