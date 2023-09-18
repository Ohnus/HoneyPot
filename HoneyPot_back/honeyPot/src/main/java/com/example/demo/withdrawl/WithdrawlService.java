package com.example.demo.withdrawl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawlService {

	@Autowired
	private WithdrawlDao dao;
	
	// 출금 총합
	public int total(String userNum) {
		return dao.getTotalWithdrawl(userNum);
	}
	
}
