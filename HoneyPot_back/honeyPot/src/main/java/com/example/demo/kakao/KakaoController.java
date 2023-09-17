package com.example.demo.kakao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/kakaoJoin")
public class KakaoController {

	@Autowired
	KakaoService kakaoservice;
	
	// 토큰 받아오기
	@GetMapping("/{code}")
	public Map kakaoCallback(@PathVariable("code")String code) {
		System.out.println("kakaoCallback");
		Map map = new HashMap<>();
		
		// 토큰 받아오기
		String access_token = kakaoservice.getToken(code);
		map.put("access_token", access_token);
		
		// 토큰으로 사용자 정보 받아오기
		KakaoProfileVo userInfo = kakaoservice.getProfile(access_token);
		map.put("userInfo", userInfo);

		return map;
	}
}
