package com.example.demo.withdrawl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import jakarta.transaction.Transactional;

@Repository
public interface WithdrawlDao extends JpaRepository<Withdrawl, Integer> {

	// 출금 총합
	@Query(value="select COALESCE(sum(w.amount), 0) from withdrawl w where w.user_num=:userNum", nativeQuery = true)
	int getTotalWithdrawl(@Param("userNum") String userNum);
}
