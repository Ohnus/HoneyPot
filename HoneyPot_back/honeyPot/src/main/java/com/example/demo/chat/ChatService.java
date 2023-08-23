package com.example.demo.chat;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ChatService {
	
	@Autowired
	private ChatDao dao;
	
	//채팅 작성
	public ChatDto add(ChatDto dto) {
		Chat entity = dao.save(new Chat(dto.getChatNum(),dto.getBoardNum(), dto.getIsFromSender(),dto.getContent(),dto.getTime()));
		
		return new ChatDto(entity.getChatNum(),entity.getBoardNum(),entity.getIsFromSender(), entity.getContent(),entity.getTime());
	}
	
	//각 채팅방 별 대화 내용 보여주기
	public ArrayList<ChatDto> getChat(int boardNum){
		
		System.out.println("getChat :  " + " boardNum : " + boardNum);
		
		ArrayList<Chat> list = (ArrayList<Chat>) dao.findByBoardNumOrderByTimeAsc(boardNum);
		ArrayList<ChatDto> list2 = new ArrayList<ChatDto>();
		for(Chat vo : list) {
			list2.add(new ChatDto(vo.getChatNum(),vo.getBoardNum(),vo.getIsFromSender(),vo.getContent(),vo.getTime()));
		}
		
		return list2;
	}
}
