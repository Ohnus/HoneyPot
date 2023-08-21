package com.example.demo.chat;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ChatDao extends JpaRepository<Chat, Integer> {
	
	//각 채팅방 별 대화 내용 보여주기
	ArrayList<Chat> findByBoardNumOrderByTimeAsc(int boardNum);

}
