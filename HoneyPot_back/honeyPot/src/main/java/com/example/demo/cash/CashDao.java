package com.example.demo.cash;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface CashDao extends JpaRepository<Cash, Integer> {

	// 캐시 총합
	@Query(value="select COALESCE(sum(c.amount), 0) as total_cash from cash c where user_num=:userNum", nativeQuery = true)
	int findTotalCashByUser(@Param("userNum") String userNum);
	
	// 유저 캐시 내역
	@Transactional
	@Modifying
	@Query(value="select * from cash where user_num=:userNum", nativeQuery = true)
	ArrayList<Cash> findAllByUser(@Param("userNum") String userNum);
}