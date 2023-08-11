package com.example.demo.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImportAccessToken {

	// RestTemplate으로 api 호출 및 토큰 값 얻기.. HTTP 클라이언트 라이브러리
	// Springboot에서 axios와 같은 기능 이용하는 것
	// https://api.iamport.kr/users/getToken
	public String getAccessToken() {
		RestTemplate restTemplate = new RestTemplate(); // HTTP 클라이언트 라이브러리.. HTTP 메서드 제공.. GET/POST/PUT/DELETE..

		HttpHeaders headers = new HttpHeaders(); // 요청 및 응답 부가정보.. json 또는 multipart 등 
		headers.setContentType(MediaType.APPLICATION_JSON);

		String API_Key = "1542181717416313"; // 내 아임포트 API KEY 
		String API_Secret = "BzFOIUL0bd1CGdHQz0HhHGC3sSMO4EFkFjpWw1nLQUKe62HOFy0XgG9lfIFcxRkqWoXTki33QehaYzq3"; // 내 아임포트 API SECRET

		Map<String, Object> map = new HashMap<>(); // Map으로 api에 전송할 데이터값 저장하기.. axios에서 formdata 역할
		map.put("imp_key", API_Key);
		map.put("imp_secret", API_Secret);
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);

		// api로 데이터 전송하기.. axios에서 url로 데이터 전송하는 기능과 비슷
		ResponseEntity<String> response = restTemplate.postForEntity("https://api.iamport.kr/users/getToken", requestEntity, String.class);

		// restTemplate으로 요청한 내용의 응답 상태 확인하기
		// is2xxSuccessful() 메서드는 200~299범위인지 확인하는 메서드
		// 따라서 해당 범위에 속한다면 요청 및 응답이 성공적이라는 것 
		if(response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody(); // api로 불러온 인증토큰 정보.. 토큰값, 현재시간, 만료시간..
			System.out.println("인증토큰 responseBody:" + responseBody);
			// responseBody는 JSON 내부에 또 다른 JSON 객체가 담겨있는 형태라서 인증토큰 추출하려면 두번 꺼내야 함
			
			// ObjectMapper 클래스.. Jackson 라이브러리에서 제공하는 Java 객체와 JSON 데이터 간의 변환을 수행하는 기능을 하는 클래스
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map2;
			String accessToken = "";
			try {
				// objectMapper: Java 객체 <-> JSON 데이터 변환하는 기능 수행
				// responseBody: JSON 형식으로 이루어진 문자열, API러 불러온 값
				// Map.class: objectMapper가 반환할 객체의 클래스 타입.. 이 경우에는 JSON을 Java map으로 변환
			    map2 = objectMapper.readValue(responseBody, Map.class);
			    // map2에 Java 객체로 변환된 JSON 데이터가 담겨있음
			    // map2에서 두 번째 JSON 객체에 있던 response라는 키를 가져온다.
			    // 내부(두 번째)에 있던 JSON 객체 타입이므로 Java map으로 캐스팅하여 realData 변수에 자바 객체로 저장하기
			    Map<String, Object> realData = (Map<String, Object>) map2.get("response");
			    // 자바 객체에 담겨있는 인증토큰을 String으로 캐스팅하여 저장하기
			    accessToken = (String) realData.get("access_token");
			} catch (JsonMappingException e) {
			    e.printStackTrace();
			} catch (JsonProcessingException e) {
			    e.printStackTrace();
			}

			System.out.println("인증토큰: " + accessToken);
			return accessToken;
		} else {
			String msg = "인증토큰 발급 실패";
			return msg;
		}
	}
	
}
