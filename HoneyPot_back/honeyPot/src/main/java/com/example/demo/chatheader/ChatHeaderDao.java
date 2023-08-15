package com.example.demo.chatheader;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.ott.Ott;

@Repository
@Transactional(readOnly = true)
public interface ChatHeaderDao extends JpaRepository<ChatHeader, Integer> {
	
	
	//pg.startCheck = 0 > 파티 대기 중
	//pg.startCheck = 1 > 파티 중
	//pg.startCheck = 2 > 파티 정상 종료
	//pg.startCheck = 3 > 파티 탈주
	//pg.startCheck = 4 > 파티 탈주
	//pg.startCheck = 5 > 파티 채팅목록 삭제
	

	//본인이 파티에 속한 채팅방 baordNum(파티중, 정상 종료된 채팅 방)
	@Query("SELECT pg.boardNum.boardNum FROM PartyGroup pg WHERE pg.userNum.userNum = :loginId AND (pg.startCheck = 1 or pg.startCheck = 2) AND pg.userNum IS NOT NULL")
    ArrayList<Integer> findboardNum( String loginId);


	//boarNum에 대한 채팅방가져오기
	ChatHeader findByBoardNum(int boardNum);
	
	// 파티 끝난 채팅 방 채팅 목록에서 삭제(pg.startcheck = 5 update)
	@Transactional
	@Modifying
	@Query("UPDATE PartyGroup pg SET pg.startCheck = 5 WHERE pg.boardNum.boardNum=:boardNum and pg.userNum.userNum =:loginId")
	void updateStartCheckTo5(@Param("boardNum") int boardNum,@Param("loginId") String loginId);
	

}
