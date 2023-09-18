package com.example.demo.payment;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.hostboard.HostBoard;
import com.example.demo.member.Member;
import com.example.demo.pending.Pending;

import jakarta.transaction.Transactional;

@Repository
public interface PaymentDao extends JpaRepository<Payment, String> {
	
	// recurringday + 결제회차 0으로 거른 리스트
	@Transactional
	@Modifying
	@Query(value="select * from payment where recurring_day=:recurringday and pay_installment=0 order by pay_installment asc, next_date asc", nativeQuery = true)
	ArrayList<Payment> findByRcrDay(@Param("recurringday") int recurringDay);
	
	// 스케쥴링 결제일(오늘)이 nextDate(다음결제일)이면서 결제회차가 마지막회차가보다 작은 애들 리스트
	@Transactional
	@Modifying
	@Query(value="select * from payment where next_date=:payDate and pay_installment < final_installment and paymentStatus=0 order by order_num asc", nativeQuery = true)
	ArrayList<Payment> findByNextDate(@Param("payDate") LocalDate paydate);
	
	@Transactional
	@Modifying
	@Query(value="select * from payment where again_date=:againDate and payment_status=-1 order by order_num asc", nativeQuery = true)
	ArrayList<Payment> findByAgainDate(@Param("againDate") LocalDate againDate);
	
	// 탈퇴신청했던(startCheck 4로 걸러진) 유저의 보드넘, 유저넘으로 해당 유저 게시글 리스트 결제회차로 최신순으로 조회 후.. 그리고 최신 글 결제여부 -2로 수정
	@Transactional
	@Modifying
	@Query(value="select * from payment where board_num=:boardNum and user_num=:userNum order by pay_installment desc", nativeQuery = true)
	ArrayList<Payment> findByBoardNumAndUserNum(@Param("boardNum") HostBoard boardNum, @Param("userNum") Member userNum);
	
	// 유저넘, 보드번호, 결제회차 -1로 리스트 조회.. 1개겠지만 서비스에서 혹시 모르니 1개로 거르기
	@Transactional
	@Modifying
	@Query(value="select * from payment where board_num=:boardNum and user_num=:userNum and pay_installment=-1", nativeQuery = true)
	ArrayList<Payment> findByBoardAndUserAndMinus1(@Param("boardNum") HostBoard boardNum, @Param("userNum") Member userNum);
	
}
