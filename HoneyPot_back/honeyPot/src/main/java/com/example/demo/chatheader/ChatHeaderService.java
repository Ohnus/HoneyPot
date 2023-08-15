package com.example.demo.chatheader;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChatHeaderService {
	
	@Autowired
	private ChatHeaderDao dao;

	// (아직 테스트 안함)
	// 파티가 시작되면 채팅 방 추가 (파티가 완성되면 partygroup의 startcheck이 0에서 1로 바뀔 때 실행)
	public ChatHeaderDto add(ChatHeaderDto dto) { 
		// dto.getBoardNum = partygroup.boardnum.boardnum(int)
		// dto.getSubject = partygroup.boardnum.type.type(string)
		// dto.getHost = partygroup.boardnum.usernum(member)
		ChatHeader entity = dao.save(new ChatHeader(dto.getChatroomNum(), dto.getBoardNum(),dto.getSubject(),dto.getHost()));
		
		return new ChatHeaderDto(entity.getChatroomNum(),entity.getBoardNum(),entity.getSubject(),entity.getHost());		
		
	}
	
	
	// 본인이 속한 파티의 채팅방 목록(파티중, 정상 종료된 채팅방)
	public ArrayList<ChatHeaderDto> getChatRoom(String loginId){
		System.out.println("getChatRoom 도착");
		ArrayList<ChatHeaderDto> list2 = new ArrayList<ChatHeaderDto>();
		ArrayList<Integer> list = dao.findboardNum(loginId);
		System.out.println("list : " + list);
		ChatHeaderDto dto = new ChatHeaderDto();
		for(Integer boardNum : list) {
			dto=null;
			System.out.println("boardNum : " + boardNum);
			ChatHeader entity = dao.findByBoardNum(boardNum);
			dto = new ChatHeaderDto(entity.getChatroomNum(),entity.getBoardNum(),entity.getSubject(),entity.getHost());
			list2.add(dto);
			
		}
		System.out.println("list2 : "+ list2);
		return list2;
	}
	
	//채팅방 객체 가져오기
	public ChatHeaderDto getChatHeader(int chatRoomNum) {
		ChatHeader ch = dao.findById(chatRoomNum).orElse(null);
		return new ChatHeaderDto(ch.getChatroomNum(),ch.getBoardNum(),ch.getSubject(),ch.getHost());
	}
	
	// 파티 끝난 채팅 방 채팅 목록에서 삭제
	public void deleteChatRoom(int boardNum, String loginId) {
		dao.updateStartCheckTo5(boardNum, loginId);
	}
	

}
