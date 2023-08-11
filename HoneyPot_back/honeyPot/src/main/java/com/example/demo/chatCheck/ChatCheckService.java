package com.example.demo.chatCheck;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatCheckService {
	
	@Autowired
	private ChatCheckDao dao;
	
	
	//한 개의 채팅을 채팅방 멤버별 튜플 생성
	public ArrayList<String> add(int boardNum, int chatNum, String loginId){
		ArrayList<String> list = (ArrayList<String>) dao.findUsersByChatroom(boardNum);
		
		
		for(String userNum: list) {
			int read = 0;
			if(loginId.equals(userNum)) {
				read = 1;
			}
			dao.save(new ChatCheck(0,chatNum,boardNum, userNum,read));
		}
		return list;
	}
	
	// 각 채팅별 안읽은 인원 확인
	public long getCountbychatNum(int chatNum) {
		long count = dao.countUnreadByChatNum(chatNum);
		return count;
	}

	// 각 채팅방 별 안읽은 채팅 개수 확인
	public long getCountbyboardNum(int boardNum, String loginId) {
		long count = dao.countUnreadByBoardNum(loginId, boardNum);
		return count;
	}
}
