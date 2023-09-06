package com.example.demo.member.naver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/naver")
public class NaverController {
	
	@Autowired
	private NaverService service;
	
	// 네이버 로그인
	@PostMapping("/login/{code}/{state}")
	  public Map naverToken(@PathVariable ("code") String code, @PathVariable ("state") String state) {
	     Map map = new HashMap();
	     System.out.println("code" + code);
	     System.out.println("state" + state);
	      
	     String access_token = service.requestToken(code, state);
	     map.put("access_token", access_token);
	     map.put("userinfo", service.userInfo(access_token));
	      
	     return map;
	}
}