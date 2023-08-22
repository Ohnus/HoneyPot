package com.example.demo.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class CertificationService {
	public HashMap getAccessToken(String impUid) {
		
		HashMap map = new HashMap<>();
		System.out.println(impUid);
		
		String impKey = "7676232421304273";
		String impSecret = "KaGIBZUzJdj4rEV1HV40xpZ7JhiKuF82a6BY2VrJjEnfoXKMDsMr2wWE1aHG3qTynOua1wxObgan8aNK";
		String strUrl = "https://api.iamport.kr/users/getToken";
		String access_token = "";
		String name = "";
		String phone = "";
//		String unique_key ="";

		try {
			URL url = new URL(strUrl);	// api 주소로 URL 객체 생성
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // URL 연결 열고, API 요청/응답용 객체 생성

			conn.setRequestMethod("POST"); // 데이터를 서버에 보낼 때 POST로
			conn.setDoOutput(true);		   // 서버로 데이터를 보내기 위해 출력 스트림을 사용하도록 설정

			conn.setRequestProperty("content-Type", "application/json"); // 요청 바디에 JSON 형식의 데이터 포함
			conn.setRequestProperty("Accept", "application/json"); 		 // 응답 데이터 타입 지정

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream())); // 서버에 데이터를 보낼 BufferedWriter 생성

			JSONObject requestData = new JSONObject();	// 데이터 담을 객체 생성
			requestData.put("imp_key", impKey);			// 생성한 객체에 imp_key(key), impKey(값) 추가
			requestData.put("imp_secret", impSecret);	// 생성한 객체에 imp_secret(key), impSecret(값) 추가

			bw.write(requestData.toString());	// JSONObject 문자열로 변환하여 씀
			bw.flush(); 						// 버퍼 내용 서버에 보냄
			bw.close();							// 버퍼 닫음

			int resposeCode = conn.getResponseCode();	// 서버로부터 응답 코드 받아옴

			System.out.println("응답코드 =============" + resposeCode);
			if (resposeCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}

				br.close();

				// 토큰 값 빼기
				String response = sb.toString();
				JsonParser parser = new JsonParser();
				JsonObject responseJson = parser.parse(response).getAsJsonObject();
				access_token = responseJson.getAsJsonObject("response").get("access_token").getAsString();
				System.out.println("Access Token: " + access_token);

				// 본인인증한 사람 정보 뺴오기
				String getPaymentUrl = "https://api.iamport.kr/certifications/" + impUid;
				HttpURLConnection getConn = (HttpURLConnection) new URL(getPaymentUrl).openConnection();
				getConn.setRequestMethod("GET");
				getConn.setRequestProperty("Content-Type", "application/json");
				getConn.setRequestProperty("Authorization", "Bearer " + access_token);

				int getResponseCode = getConn.getResponseCode();
				System.out.println("GET 응답코드 ============= " + getResponseCode);

				if (getResponseCode == 200) { // 성공
					BufferedReader getBr = new BufferedReader(new InputStreamReader(getConn.getInputStream()));
					StringBuilder getResponseSb = new StringBuilder();
					String getLine;
					while ((getLine = getBr.readLine()) != null) {
						getResponseSb.append(getLine).append("\n");
					}
					getBr.close();

					String getResponse = getResponseSb.toString();
					System.out.println("GET 응답 결과: " + getResponse);
					JsonParser parser1 = new JsonParser();
					JsonObject phoneJson1 = parser1.parse(getResponse).getAsJsonObject();
					
					// 이름 값 빼기
					name = phoneJson1.getAsJsonObject("response").get("name").getAsString();
					System.out.println("이름>>>>>" + name);
					map.put("name", name);

					// 전화번호 값 빼기
					phone = phoneJson1.getAsJsonObject("response").get("phone").getAsString();
					System.out.println("phone: " + phone);
					map.put("phone", phone);
					
//					// 유니크키 값 빼기: 테스트 버전이라 response 데이터에 없는 듯
//					unique_key = phoneJson1.getAsJsonObject("response").get("unique_key").getAsString();
//					System.out.println("unique_key: " + unique_key);
//					map.put("unique_key", unique_key);

				} else {
					System.out.println("GET 에러 응답 메시지: " + getConn.getResponseMessage());
				}
			} else {
				System.out.println(conn.getResponseMessage());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

}
