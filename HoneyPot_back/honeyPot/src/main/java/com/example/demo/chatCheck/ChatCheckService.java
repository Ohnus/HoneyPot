package com.example.demo.chatCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatCheckService {
	
	@Autowired
	private ChatCheckDao dao;
	
	// 각 채팅별 안읽은 인원 확인
	public long getCount(int chatNum) {
		long count = dao.countUnreadByChatNum(chatNum);
		return count;
	}

	// 각 채팅방 별 안읽은 채팅 개수 확인
}
