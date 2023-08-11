package com.example.demo.chatheader;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatHeaderDao extends JpaRepository<ChatHeader, Integer> {

	//본인이 파티에 속한 채팅방 baordNum(파티중, 정상 종료된 채팅 방)
	@Query("SELECT pg.boardNum.boardNum FROM PartyGroup pg WHERE pg.userNum.userNum = :loginId AND (pg.startCheck = 1 or pg.startCheck = 2) AND pg.userNum IS NOT NULL")
    ArrayList<Integer> findboardNum(String loginId);
	
	//boarNum에 대한 채팅방
	ChatHeaderDto findByBoardNum(int boardNum);
}
