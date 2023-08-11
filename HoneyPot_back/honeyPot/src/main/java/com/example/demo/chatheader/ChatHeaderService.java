package com.example.demo.chatheader;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChatHeaderService {
	
	@Autowired
	private ChatHeaderDao dao;

	// 파티가 시작되면 채팅 방 추가
	
	// 본인이 속한 파티의 채팅방 목록(파티중, 정상 종료된 채팅방)
	public ArrayList<ChatHeaderDto> getChatRoom(String loginId){
		ArrayList<ChatHeaderDto> list2 = new ArrayList<ChatHeaderDto>();
		ArrayList<Integer> list = dao.findboardNum(loginId);
		for(Integer boardNum : list) {
			
			ChatHeaderDto vo = dao.findByBoardNum(boardNum);
			list2.add(vo);
		}
		return list2;
	}
}
