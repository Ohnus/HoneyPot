package com.example.demo.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ImportPayments {
	
	// /payments/again api.. 빌링키 존재할 경우 해당 빌링키로 (재)결제 하는 api
	public int doPayment(String accessToken, String customerUid, String merchantUid, int amount, String name) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(accessToken);
		
		Map<String, Object> map = new HashMap<>();
		map.put("customer_uid", customerUid); // 유저빌링키(billingKey)
		map.put("merchant_uid", merchantUid); // 주문번호(orderNum)
		map.put("amount", amount); // 주문금액(100원, 월구독료, 보증금, 수수료..)
		map.put("name", name); // 주문명
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity("https://api.iamport.kr/subscribe/payments/again", requestEntity, String.class);
		
		int msg = 0;
		if(response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody();
			System.out.println("결제 responseBody: " + responseBody);
			return msg;
		} else {
			msg = 1;
			return msg;
		}
	}
	
	// /payments/cancel api.. 결제취소 api.. but 테스트 모드라서 부분취소 불가능, 전액취소 후 결제api로 일할계산 부분만큼 재결제 유도
	public int cancelPayment(String accessToken, String merchantUid, int amount, int checksum) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(accessToken);
		
		Map<String, Object> map = new HashMap<>();
		map.put("merchant_uid", merchantUid); // 주문번호(orderNum)
		map.put("amount", amount); // 결제금액
		map.put("checksum", checksum); // 취소 가능한 잔액
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity("https://api.iamport.kr/payments/cancel", requestEntity, String.class);
		
		int msg = 2;
		if(response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody();
			System.out.println("결제취소 response: " + responseBody);
			return msg;
		} else {
			msg = 3;
			return msg;
		}
		
	}
	
}