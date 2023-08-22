package com.example.demo.pending;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.hostboard.HostBoard;
import com.example.demo.member.Member;

import jakarta.transaction.Transactional;

@Repository
public interface PendingDao extends JpaRepository<Pending, Integer> {
	
	// 구독료 리턴 리스트
	@Transactional
	@Modifying
	@Query(value="select * from pending where pay_date=:earningDate and msg=0 order by pending_num", nativeQuery = true)
	ArrayList<Pending> findByEarningDate(@Param("earningDate") LocalDate earningDate);
	
	// 보증금 리턴 리스트
	@Transactional
	@Modifying
	@Query(value="select * from pending where return_date=:returnDate and msg=1 order by pending_num", nativeQuery = true)
	ArrayList<Pending> findByReturnDate(@Param("returnDate") LocalDate returnDate);
	
	// 보드넘, 유저넘, msg로 위약금 단건 조회
	@Query(value="select p from pending where p.board_num=:boardNum and p.user_num=:userNum and msg=1", nativeQuery = true)
	Pending findByBoardNumAndUserNumAnsMsg(@Param("boardNum") HostBoard boardNum, @Param("userNum") Member userNum);
}
