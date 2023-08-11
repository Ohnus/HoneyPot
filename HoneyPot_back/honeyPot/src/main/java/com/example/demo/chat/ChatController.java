package com.example.demo.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.chatCheck.ChatCheckService;

@RestController
@Controller
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatService service;
	
	@Autowired
	private ChatCheckService CCservice;
	
	//채팅 작성
	@PostMapping("/{loginId}")
	public Map add(ChatDto dto, @PathVariable("loginId") String loginId) {
		Map map = new HashMap();
		boolean flag = true;
		ChatDto dto2 = null;
		
		try{
		
		// 채팅 한 개 chat테이블에 추가
		dto2 = service.save(dto); 
		
		//채팅 한 개에 대해서 각 채팅방 멤버 별 인원수만큼 chatcheck(읽음 여부 확인하기 위한)테이블에 튜플 추가
		CCservice.add(dto.getBoardNum(),dto.getChatNum(),loginId); 
		
		}catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		map.put("dto", dto2);
		System.out.println("채팅보내기 : " + dto2);
		return map;
	}
	
	//각 채팅방 별 대화 내용 보여주기
	@GetMapping("")
	public Map getchat(int boardNum) {
		ArrayList<ChatDto> list = service.getChat(boardNum);
		Map map = new HashMap();
		map.put("chat", list);
		
		return map;
	}
}