package com.example.demo.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private JwtTokenProvider tokenprovider;
	
	@Value("${spring.servlet.multipart.location}")
	private String path;	// C:/honeypot/
	
	
	// 회원가입: 본인인증(이름+핸드폰번호) 값으로 가입 이력 여부 확인
	@PostMapping("")		
	public Map join(MemberDto dto, int snsType) {
		Map map = new HashMap();
		
		String checkName = dto.getName();
		String checkPhone = dto.getPhone();

		MemberDto joinCheck = service.getUser(checkPhone);	// 핸드폰 번호로 유저 정보 검색
		
		String checkName2 = joinCheck.getName();
		String checkPhone2 = joinCheck.getPhone();
		String id = joinCheck.getEmail();
		
		if (checkPhone2 != null && checkName.equals(checkName2)) {	// 핸드폰번호 존재 여부 + 해당 핸드폰번호의 소유주와 이름이 일치하는지 확인
			map.put("msg", "해당 정보로 이미 가입된 상태입니다. \n" + id + "로 로그인해주세요.");
			
		} else if (checkPhone2 != null) {
			map.put("msg", "해당 핸드폰번호로 가입된 이름이 존재합니다. \n" + "고객센터로 문의해주세요.");
			
		} else {
			String userNum = service.generateRandomUserNum(snsType);	// userNum 난수 생성
			dto.setUserNum(userNum);									// 생성된 난수 dto에 저장
					
			MemberDto dto2 = service.save(dto);
			map.put("dto", dto2);
		}
		
		return map;
	}
	
	
	// 로그인
	@PostMapping("/login")
	public Map login(String email, String pwd) {
		Map map = new HashMap();
		
		boolean flag = false;
		MemberDto dto = service.getUser(email);
		
		if (dto != null && pwd.equals(dto.getPwd())) {
			String token = tokenprovider.generateJwtToken(dto);
			flag = true;
			map.put("token", token);
		}
		map.put("flag", flag);
		return map;

	}
	
	
	// 내 정보 수정: 비밀번호, 이메일(인증), 전화번호(본인인증), 닉네임, 프로필 이미지

}
