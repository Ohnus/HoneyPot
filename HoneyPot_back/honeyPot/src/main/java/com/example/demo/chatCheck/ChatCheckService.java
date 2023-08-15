package com.example.demo.chatCheck;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatCheckService {
	
	@Autowired
	private ChatCheckDao dao;
	
	
	//한 개의 채팅을 채팅방 멤버별 튜플 생성
	public ArrayList<ChatCheckDto> add(int boardNum, int chatNum, String loginId){
		System.out.println("ccservice 요청옴");
		System.out.println("boardNum : " + boardNum);
		System.out.println("loginId : " + loginId);
		System.out.println("chatNum : " + chatNum);
		ArrayList<String> list = (ArrayList<String>) dao.findUsersByChatroom(boardNum);
		System.out.println("ccservice : " + list);
		
		ArrayList<ChatCheckDto> list2 = new ArrayList<ChatCheckDto>();
		for(String userNum: list) {
			int read = 0;
			if(loginId.equals(userNum)) {
				read = 1;
			}
			
			ChatCheck entity = dao.save(new ChatCheck(0,chatNum,boardNum, userNum,read));
			list2.add(new ChatCheckDto(entity.getCheckNum(), entity.getChatNum(),entity.getBoardNum(), entity.getToReceiver(),entity.getRead()));
		}
	
		return list2;
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
	
	// 채팅방 클릭 했을 때 모두 읽음 처리
	public void changeRead(int boardNum, String loginId) {
		dao.updateRead(loginId, boardNum);
	}
	

}
