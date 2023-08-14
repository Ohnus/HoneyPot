package com.example.demo.chatCheck;

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


@RestController
@Controller
@RequestMapping("/chatcheck")
public class ChatcheckController {
	
	@Autowired
	private ChatCheckService service;
	
	// 각 채팅별 안읽은 인원 확인
	@GetMapping("/countchat/{chatNum}")
	public Map countcheck( @PathVariable("chatNum") int chatNum) {
		long count = service.getCountbychatNum(chatNum);
		Map map = new HashMap();
		map.put("count", count);
		return map;
	}
	
	// 각 채팅방 별 안읽은 채팅 개수 확인
	@GetMapping("/countchatroom/{boardNum}/{loginId}")
	public Map countcheckroom( @PathVariable("boardNum") int boardNum, @PathVariable("loginId") String loginId) {
		long count = service.getCountbyboardNum(boardNum, loginId);
		Map map = new HashMap();
		map.put("count", count);
		return map;
		}
	
	//한 개의 채팅을 채팅방 멤버별 튜플 생성 test용
	@PostMapping("/{boardNum}/{chatNum}/{loginId}")
	public Map test( @PathVariable("boardNum") int boardNum,@PathVariable("chatNum") int chatNum, @PathVariable("loginId") String loginId) {
		ArrayList<ChatCheckDto> list = service.add(boardNum,chatNum,loginId); 
		Map map =new HashMap();
		map.put("list",list);
		return map;
	}
	
		
	

	
	
}
