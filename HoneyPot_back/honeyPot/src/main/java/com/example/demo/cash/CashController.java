package com.example.demo.cash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.withdrawl.WithdrawlService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cashs")
public class CashController {
	
	@Autowired
	private CashService cashService;
	
	@Autowired
	private WithdrawlService wdServcie;
	
	// 캐시총합 - 출금총합 = 보유캐시
	@GetMapping("/getCash/{userNum}")
	public Map getCash(@PathVariable("userNum") String userNum) {
		Map map = new HashMap<>();
		
		int totalCash = cashService.total(userNum);
		int totalWithdrawl = wdServcie.total(userNum);

		System.out.println("보유캐시: " + totalCash);
		
		System.out.println("출금액: " + totalWithdrawl);
		
		int havingCash = totalCash - totalWithdrawl;
		
		map.put("havingCash", havingCash);
		
		return map;
	}
	
	// 캐시 내역
	@GetMapping("/cashList/{userNum}")
	public Map getList(@PathVariable("userNum") String userNum) {
		Map map = new HashMap<>();
		
		ArrayList<CashDto> list = cashService.listByUser(userNum);
		System.out.println("캐시 리스트: " + list);
		map.put("list", list);
		
		return map;
	}
}
