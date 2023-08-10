package com.example.demo.chatCheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatCheckDao extends JpaRepository<ChatCheck, Integer> {
	
	// 각 채팅별 안읽은 인원 확인
	@Query("SELECT COUNT(cc) FROM ChatCheck cc WHERE cc.chatNum = :chatNum AND cc.read = 0")
    long countUnreadByChatNum(@Param("chatNum") int chatNum);
	
	// 각 채팅방 별 안읽은 채팅 개수 확인
}
