package com.example.demo.chatheader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/chatheader")
public class ChatHeaderController {
	
	@Autowired
	private ChatHeaderService service;
	
	// 본인이 속한 파티의 채팅방 목록(파티중, 정상 종료된 채팅방) (테스트 완)
	@GetMapping("/{loginId}")
	public Map getChatRoom(@PathVariable("loginId") String loginId) {
		System.out.println("loginId : " + loginId);
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<ChatHeaderDto> list = null;
		try {
		 list = service.getChatRoom(loginId);
		}catch(Exception e) {
			flag = false;
		}
		map.put("list", list);
		map.put("flag", flag);
		
		return map;
		
	}
	
	// 파티장은 채팅방 이름 수정 가능 ( 테스트 완 )
	@PutMapping("")
	public Map changeSubject(ChatHeaderDto dto) {
		System.out.println("채팅방 수정");
		boolean flag = true;
		ChatHeaderDto old = null;
		try {
		old = service.getChatHeader(dto.getChatroomNum());
		old.setSubject(dto.getSubject());
		}catch(Exception e) {
			flag = false;
		}
		ChatHeaderDto newdto = service.add(old);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("newdto", newdto);
		
		return map;
		
	}
	
	// 파티 끝난 채팅 방 채팅 목록에서 삭제(데이터는 삭제되지 않고 partygroup의 startcheck이 5로 수정)( 테스트 완 )
	@PutMapping("/{loginId}/{boardNum}")
	public void deleteChatRoom(@PathVariable("boardNum") int boardNum, @PathVariable("loginId") String loginId) {
	
		service.deleteChatRoom(boardNum, loginId);
	}

}
