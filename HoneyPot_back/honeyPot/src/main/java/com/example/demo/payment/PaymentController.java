package com.example.demo.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	private ImportAccessToken accessTokenAPI;
	
	@Autowired
	private ImportBillingKey billingKeyAPI;
	
	@Autowired
	private ImportPayments paymentsAPI;
	
	@Autowired
	private PaymentService pservice;

	@Autowired
	private MemberService mservice;

	// 결제수단 등록 or 변경
	@PostMapping("/registerCards")
	public Map saveBillingKey(String cardNum, String cardExpiry, String birth, String cardPwd, String billingKey, String userNum) {
		// 클라이언트 사이드에서 카드정보 + 유저빌링키 서버사이드로 전송
		System.out.println("카드번호: " + cardNum);
		System.out.println("유효기간: " + cardExpiry);
		System.out.println("생년월일: " + birth);
		System.out.println("비밀번호: " + cardPwd);
		System.out.println("유저빌링키: " + billingKey);
		System.out.println("회원번호: " + userNum);

		Map map = new HashMap<>();

		try {
			// api key, secret key로 메서드에서 인증토큰 발급..
			String accessToken = accessTokenAPI.getAccessToken();

			// 카드정보 + 유저빌링키 + 인증토큰으로 pg사 빌링키 발급..
			String customerUid = billingKey; // 유저빌링키
			
			// pg사 빌링키.. 사실 내가 쓸 일은 없지만 값 잘 받아오나 궁금해서 메서드 호출함
			String pgBillingKey = billingKeyAPI.getBillingKey(accessToken, customerUid, cardNum, cardExpiry, birth, cardPwd);

			// 유저빌링키 유저정보에 저장.. 유경누나랑 얘기해보고 서비스 불러와야지..
			MemberDto mdto = mservice.getUser(userNum);
			System.out.println("before mdto: " + mdto);
			mdto.setBillingKey(billingKey);
			mdto = mservice.save(mdto);
			System.out.println("after mdto: " + mdto);
			
			map.put("msg", "성공");
			map.put("accessToken", accessToken);
		} catch (Exception e) {
			map.put("msg", "실패" + e.getMessage());
		}

		return map;
	}
	
	// 파티 생성, 찾기 마무리 단계에서 100원 결제/환불
	// again api + 유저빌링키 이용하기
	
	
	
	// 정기결제.. scheduled annotation.. localdate + recurringday 비교
	// 월구독료 기준으로 보증금, 수수료 계산해서 유저빌링키 이용해서 정기결제!
	// 예약 api를 쓸까, scheduled + again api를 쓸까 고민..
	
	// 결제취소
}
