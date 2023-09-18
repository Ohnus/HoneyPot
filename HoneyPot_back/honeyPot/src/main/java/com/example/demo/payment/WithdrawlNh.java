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
public class WithdrawlNh {
	
	// /payments/again api.. 빌링키 존재할 경우 해당 빌링키로 (재)결제 하는 api
	public String withdrawl() {
		
		String AccessToken = "438e04756620d168cd612045dce5fda843ab943b593a39602f7ff473d707244f";
		String ApiNm = "ReceivedTransferOtherBank";
		String Tsymd = "20230814";
		String Trtm = "112428";
		String Iscd = "001977";
		String FintechApsno = "001";
		String APISvcCd = "ReceivedTransferA";
		String IsTuno = "0004";
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AccessToken);
		headers.set("ApiNm", ApiNm);
		headers.set("Tsymd", Tsymd);
		headers.set("Trtm", Trtm);
		headers.set("Iscd", Iscd);
		headers.set("FintechApsno", FintechApsno);
		headers.set("APISvcCd", APISvcCd);
		headers.set("IsTuno", IsTuno);
		
		Map<String, Object> map = new HashMap<>();
		map.put("Bncd", "003"); 
		map.put("Acno", "1000002280003");
		map.put("Tram", "30000"); 
		map.put("DractOtlt", "ㅁㅁㅁ 고객님 캐시 인출"); 
		map.put("MractOtlt", "8월 구독료");
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity("https://developers.nonghyup.com/ReceivedTransferOtherBank.nh", requestEntity, String.class);
		
		if(response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody();
			System.out.println("출금 responseBody: " + responseBody);
			return responseBody;
		} else {
			String responseBody = response.getBody();
			String msg = "출금 실패";
			return msg;
		}
	}
}
