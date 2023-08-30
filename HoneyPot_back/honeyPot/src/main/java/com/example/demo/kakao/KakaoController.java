package com.example.demo.kakao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/kakaoJoin")
public class KakaoController {

	// 토큰 받아오기
	@GetMapping("/{code}")
	public Map kakaoCallback(@PathVariable("code")String code) {
		System.out.println("kakaoCallback");
		
		String grant_type = "authorization_code";
		String client_id = "d836a71e352daf8a9987274ee1b74912";
		String redirect_uri = "http://localhost:8989/KakaoJoin";
		String client_secret = "tzKsj2HEeG3WOvc5eoiO7CanXrhZgPZr";
		
		
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 객체 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 객체 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", grant_type);
		params.add("client_id", client_id);
		params.add("redirect_uri", redirect_uri);
		params.add("code", code);
		params.add("client_secret", client_secret);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
				new HttpEntity<>(params, headers);
		
		//Http 요청하기 - post방식으로
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class);
		
		System.out.println("response : " + response);
				Map map = new HashMap();
		map.put("response", response);
		return map;
	}
}
