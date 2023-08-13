package com.example.demo.partygroup;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.automatching.AutoMatching;
import com.example.demo.hostboard.HostBoard;
import com.example.demo.member.Member;

import jakarta.transaction.Transactional;

@Repository
public interface PartyGroupDao extends JpaRepository<PartyGroup, Integer> {

	//본인이 참여하고 있는 파티가 뭐가 있는 지 확인 하기 위한 것 
	ArrayList<PartyGroup> findByUserNum(Member userNum);
	
	//몇개의 boardNum이 존재하는가 확인 
	ArrayList<PartyGroup> findByBoardNum(HostBoard boardNum);
	
	//0 이었던 startcheck 를 시작인 1(파티시작)로 변경 해야 함. 
	@Transactional
	@Modifying
	@Query(value = "update party_group set start_check=1 where board_num=:board_num", nativeQuery = true)
	void updateStartCheckTo1(@Param("board_num") int board_num);
	
	//1 이었던 startcheck 를 종료인 2(파티종료)로 변경 해야 함. 
	@Transactional
	@Modifying
	@Query(value = "update party_group set start_check=2 where board_num=:board_num and (start_check = 1 or start_check = 4)", nativeQuery = true)
	void updateStartCheckTo2(@Param("board_num") int board_num);
	
	//1 이었던 사람의 startcheck 를 탈주인 3으로 변경 해야 함. 
	@Transactional
	@Modifying
	@Query(value = "update party_group set start_check=3 where board_num=:board_num",  nativeQuery = true)
	void updateStartCheckTo3(@Param("board_num") int board_num);
	
	//1 이었던 사람의 startcheck를 중간 탈주인 4로 변경 해야 함 
	@Transactional
	@Modifying
	@Query(value = "update party_group set start_check=4 where board_num=:board_num and user_num=:user_num",  nativeQuery = true)
	void updateStartCheckTo4(@Param("board_num") int board_num,@Param("user_num") String user_num);
	
	// 0 (구독 시작전)인 해당 boardNum을 가진 행을 삭제
	@Transactional
	@Modifying
	@Query(value = "delete from party_group where board_Num=:board_num and start_check = 0", nativeQuery = true)
	void deleteByBoardNum(@Param("board_num") int board_num);
	
	//statcheck 별로 정렬 
	@Transactional
	@Modifying
	@Query(value = "select * from party_group where board_Num=:board_num and start_check=:start_check", nativeQuery = true)
	ArrayList<PartyGroup> selectStartCheck (@Param("board_num") int board_num, @Param("start_check") int start_check);

	@Transactional
	@Modifying
	@Query(value = "select * from party_group where board_num=:board_num", nativeQuery = true)
	ArrayList<PartyGroup> findByBoardNumUsingInt(@Param("board_num") int board_num);
	
	@Transactional
	@Modifying
	@Query(value = "select * from party_group where matching_num=:matching_num", nativeQuery = true)
	boolean findByMatchingNum(@Param("matching_num") int matching_num);

}
