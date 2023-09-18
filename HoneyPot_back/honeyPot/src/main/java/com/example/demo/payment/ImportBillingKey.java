package com.example.demo.payment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ImportBillingKey {

	// 카드정보 + 유저빌링키 + 인증토큰으로 pg사 빌링키 발급..
	// RestTemplate으로 api 호출 및 빌링키 발급
	// https://api.iamport.kr/subscribe/customers/customer_uid
	public String getBillingKey(String accessToken, String customerUid, String cardNum, String cardExpiry, String birth, String cardPwd) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(accessToken); // 아임포트 빌링키 발급 API 설명 참고.. 인증토큰 부가 정보로 보내기..

		// 결제 수단 등록할 때 빌링키 발급에 필요한 정보: 카드번호, 만료일, 유저생년월일, 카드 비밀번호 앞 2자리, 내가 이용할 PG사 MID
		// formdata처럼 보내기~
		Map<String, Object> map = new HashMap<>();
		map.put("card_number", cardNum);
		map.put("expiry", cardExpiry);
		map.put("birth", birth);
		map.put("pwd_2digit", cardPwd);
		map.put("pg", "nice_v2.iamport01m");
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("https://api.iamport.kr/subscribe/customers/" + customerUid, requestEntity, String.class);

		if(response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody();
			System.out.println("빌링키 responseBody: " + responseBody);
			
			// pg사에서 받아 온 정보는 내가 쓸 일은 없으므로 Java 객체로 변환은 하지 않음..
			return responseBody;
		} else {
			String msg = "빌링키 발급 실패";
			return msg;
		}
	}
	
	// 빌링키 조회해서 카드사 이름 얻기
	public String getCardName(String accessToken, String customerUid) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		
		Map<String, Object> map = new HashMap<>();
		map.put("customerUid", customerUid);
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);
		System.out.println("유저빌링키: " + customerUid);
		System.out.println("토큰: " + accessToken);
		
		ResponseEntity<String> response = restTemplate.getForEntity("https://api.iamport.kr/subscribe/customers/" + customerUid + "?_token=" + accessToken, String.class);
		
		String responseBody = "";
		String billingKeyStatus = "";
		if(response.getStatusCode().is2xxSuccessful()) {
			responseBody = response.getBody();
			System.out.println("빌링키 정보 responseBody: " + responseBody);
			try {
				JSONObject parseJson = new JSONObject(responseBody);
				billingKeyStatus = parseJson.getJSONObject("response").getString("card_name");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("결제 성공: " + billingKeyStatus);
		} else {
			billingKeyStatus = "등록된 결제수단이 없습니다.";
		}
		return billingKeyStatus;
	}
	
	// 빌링키 PG사 데이터에서 삭제
	public String delBillingKey(String accessToken, String customerUid, String userNum) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		
		Map<String, Object> map = new HashMap<>();
		map.put("customerUid", customerUid);
		map.put("reason", "회원 결제수단 삭제");
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("https://api.iamport.kr/subscribe/customers/" + customerUid, HttpMethod.DELETE, requestEntity, String.class);
		
		if(response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody();
			System.out.println("빌링키 삭제 responseBody: " + responseBody);
			return responseBody;
		} else {
			String msg = "빌링키 삭제 실패";
			return msg;
		}
	}
}
