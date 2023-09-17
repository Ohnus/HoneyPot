package com.example.demo.chatheader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.MemberDto;

@RestController
@Controller
@CrossOrigin(origins = "*")
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
		
		System.out.println("list : " + list);
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
	
	//boardNum채팅방 참여 멤버 목록 가져오기
	@GetMapping("/getmembers/{boardNum}")
	public Map getMembers(@PathVariable("boardNum") int boardNum) {
		boolean flag = true;
		ArrayList<MemberDto> members = null;
		try {
		members = (ArrayList<MemberDto>)service.getMembers(boardNum);
		}catch(Exception e){
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("members", members);
		return map;
	}
	
	//해당 boardNum의 ott type 가져오기
	@GetMapping("/otttype/{boardNum}")
	public Map getOttType(@PathVariable("boardNum") int boardNum) {
		System.out.println("otttype 컨트롤러");
		boolean flag = true;
		String otttype = null;
		try {
			otttype = service.getOttType(boardNum);
			System.out.println("otttype : " + otttype);
			
		}catch(Exception e) {
			flag = false;
		}
		
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("otttype", otttype);
		return map;
		
	}
	

}
