package com.example.demo.kakao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
		System.out.println("카카오 리프레쉬 토큰 : " + KakaoToken.getRefresh_token());
		String access_token = KakaoToken.getAccess_token();
		
		return access_token;
	}
	
	// 사용자 정보 가져오기
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
	
	public KakaoProfileVo getProfile2(String access_token) {
		
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		String result = "";
		
		
		try {
			// URL 객체 만들기
			URL url = new URL(reqURL);
			
			//URL(HTTP 프로토콜을 사용)을 통해 서버와 통신할때 HttpURLConnection클래스 사용
			// 웹페이지 업로드 / 다운로드, HTTP요청 및 응답 등
			
			
			//HttpURL 연결 생성
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//요청방식 설정
			conn.setRequestMethod("POST");
			
			//HttpHeader 작성
			conn.setRequestProperty("Authorization", "Bearer " + access_token);
			
			//서버에서 콘텐츠를 읽는 데 사용할 수 있는지 여부 설정
			conn.setDoInput(true);
			
			//결과 코드 출력
			System.out.println(conn.getResponseCode());
			
			//입력 스트림 가져오기 및 데이터 읽기
			// 네트워크 연결 : connect()메서드가 호출되어 명시적으로 이루어지거나, 입력스트림/출력스트림을 가져올 때 암시적으로 이루어짐.
			// BufferedReader : 바이트를 문자열로 변환
			int resCode = conn.getResponseCode();
			if(resCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			result = br.readLine();
			}
			System.out.println("result : " + result);
			//br.close(); BufferedReader는 사용하지 않으면 자원관리해주는 GC의 대상이 되기 때문에 직접 close()하지 않아도 됨
			// BufferedWriter는 사용 후 close()해줘야 함
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ObjectMapper objectMapper3=new ObjectMapper();
		KakaoProfileVo kakaoProfile = null;
		try {
			kakaoProfile = objectMapper3.readValue(result, KakaoProfileVo.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	
		return kakaoProfile;
	}
}
