package com.example.demo.chatCheck;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatCheckDao extends JpaRepository<ChatCheck, Integer> {
	
	//한개의 채팅을 채팅방 멤버별 튜플 생성할 멤버들 목록
	@Query("SELECT pg.userNum.userNum FROM PartyGroup pg WHERE pg.boardNum = :boardNum AND pg.startCheck = 1 AND pg.userNum IS NOT NULL")
    ArrayList<String> findUsersByChatroom(@Param("boardNum") int boardNum);

	
	
	// 각 채팅별 안 읽은 인원 확인
	@Query("SELECT COUNT(cc) FROM ChatCheck cc WHERE cc.chatNum = :chatNum AND cc.read = 0")
    long countUnreadByChatNum(@Param("chatNum") int chatNum);
	
	// 각 채팅방 별 안읽은 채팅 개수 확인
	@Query("SELECT COUNT(cc) FROM ChatCheck cc WHERE cc.toReceiver = :loginId AND cc.boardNum = :boardNum AND cc.read = 0")
    long countUnreadByBoardNum(String loginId, @Param("boardNum") int boardNum);
	
	
}
