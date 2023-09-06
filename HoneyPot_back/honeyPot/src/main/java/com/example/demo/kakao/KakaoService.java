package com.example.demo.kakao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoService {
	
	// 카카오 토큰 받아오기
	public String getToken(String code){
		
		String grant_type = "authorization_code";
		String client_id = "d836a71e352daf8a9987274ee1b74912";
		String redirect_uri = "http://localhost:8989/KakaoJoin";
		String client_secret = "tzKsj2HEeG3WOvc5eoiO7CanXrhZgPZr";
		
		
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 객체 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 객체 생성,
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
		System.out.println("response.getBody() : " + response.getBody());
		
		// json데이터를 자바 오브젝트에서 처리함
		ObjectMapper objectMapper=new ObjectMapper();
		KakaoTokenVo KakaoToken = null;
		try {
			KakaoToken = objectMapper.readValue(response.getBody(), KakaoTokenVo.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : " + KakaoToken.getAccess_token());
		
		String access_token = KakaoToken.getAccess_token();
		
		return access_token;
		
	}
	
	public KakaoProfileVo getProfile(String access_token) {
		
		RestTemplate rt2 = new RestTemplate();
		
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Content-type","application/x-www-form-urlencoded");
		headers2.add("Authorization","Bearer " + access_token);

		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoEmailRequest =
		new HttpEntity<>(headers2);
		
		//Http 요청하기 - post방식으로
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoEmailRequest,
				String.class);
		System.out.println("response2.getBody : " + response2.getBody());
		
		
		// json데이터를 자바 오브젝트에서 처리함
		ObjectMapper objectMapper2=new ObjectMapper();
		KakaoProfileVo kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfileVo.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("kakaoProfile : " +  kakaoProfile );
		System.out.println("카카오 아이디 : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		
		
		
		return kakaoProfile;
		
	}
}
