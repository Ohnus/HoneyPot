package com.example.demo.member.naver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NaverService {

	public String requestToken(String code, String state) {
		String access_Token = "";
		String refresh_Token = "";
		String strUrl = "https://nid.naver.com/oauth2.0/token"; // 토큰 요청 보낼 주소
		NaverTokenVo NaverToken = new  NaverTokenVo(); // 요청받을 객체


		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();

			sb.append("grant_type=authorization_code");
			sb.append("&client_id=XUfF5HOL1SVNILfyWR1b");
			sb.append("&client_secret=h0OFMLCbdH");
			sb.append("&code=" + code);
			sb.append("&state=" + state);
			
			bw.write(sb.toString());
			bw.flush();
			
			int responseCode = conn.getResponseCode();
			log.info("responsecode(200이면성공): {}", responseCode);

			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			
			while ((line = br.readLine()) != null) {
				result += line;
			}

			log.info("response body: {}", result);

			ObjectMapper mapper = new ObjectMapper();
			NaverToken = mapper.readValue(result, NaverTokenVo.class);
			System.out.println(NaverToken);


			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("네이버생성완료>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		return NaverToken.getAccess_token();

	}
	
	//네이버유저정보얻기 
	public Map userInfo(String access_token){
		HashMap userInfo = new HashMap<>();

		String email = "";
		String id ="";
		log.info("유저정보 요청 시작");
		String strUrl =  "https://openapi.naver.com/v1/nid/me"; // request를 보낼 주소
		userInfo.put("accessToken", access_token);

		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// outputStreamm으로 post 데이터를 넘김

			// 전송할 header 작성, 인자로 받은 access_token전송
			conn.setRequestProperty("Authorization", "Bearer " + access_token);

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("requestUser의 responsecode(200이면성공): {}", responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();

			log.info("response body: {}", result);
			
			
			//이제 여기서부터는 result 이걸 json 파싱한 후 
			// 결과를 HashMap 형태로 변환해서 
			//필요한 값들을 가져와서 
			//Map 에 담아서 다시 컨트롤러로 return 하면 끝
			
			
		    JSONObject jsonResponse = null;
			try {
				jsonResponse = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				email = jsonResponse.getJSONObject("response").getString("email");
				id = jsonResponse.getJSONObject("response").getString("id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    // email 값을 userInfo 맵에 추가
		    userInfo.put("email", email);
		    userInfo.put("id", id);
			
			System.out.println(email);
			System.out.println(id);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInfo;

		
	}
	
}