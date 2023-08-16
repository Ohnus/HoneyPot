package com.example.demo.payment;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
	@Query(value="select * from payment where next_date=:payDate and pay_installment < final_installment order by order_num asc", nativeQuery = true)
	ArrayList<Payment> findByNextDate(@Param("payDate") LocalDate paydate);
	
}
