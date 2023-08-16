package com.example.demo.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/members")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private CertificationService certificationService;
	
	@Autowired
	private JwtTokenProvider tokenprovider;
	
	@Autowired
	private EmailService emailService;
	
	@Value("${spring.servlet.multipart.location}")
	private String path;	// C:/honeypot/
	
	
	// 본인인증 통한 회원가입 이력 여부 확인
	@GetMapping("/certifications/redirect")
	public Map handleRedirect(@RequestParam("imp_uid") String impUid) {
		boolean certified = true;		// 중복된 핸드폰번호 + 이름 없음
		boolean duplicated1 = false;	// 중복된 핸드폰번호 + 이름 있음
		boolean duplicated2 = false;	// 중복된 핸드폰번호 있음
		Map map = new HashMap<>();
		
		// 본인인증 정보 가져오기
		map = certificationService.getAccessToken(impUid);

		String certifiName = (String) map.get("name");
		String certifiPhone = (String) map.get("phone");
		
		System.out.println(certifiName + " / " + certifiPhone);		
		
		
		// 전체 멤버 DB 정보 가져오기
		ArrayList<MemberDto> dtoList = service.getAllUser();
		
		System.out.println("dtoList: " + dtoList);
		

		if (dtoList != null) {
			for (MemberDto list : dtoList) {
				if (list.getPhone().equals(certifiPhone) && list.getName().equals(certifiName)) {
					System.out.println("이름 + 핸드폰 값 중복");
					duplicated1 = true;
					certified = false;  
					break;
				} else if (list.getPhone().equals(certifiPhone)) {
					System.out.println("핸드폰 값 중복");
					duplicated2 = true;
					certified = false;  
					break;
				}
				
			}			
		
		}

		map.put("certified", certified);
		map.put("duplicated1", duplicated1);
		map.put("duplicated2", duplicated2);
		
		map.put("name", certifiName);
		map.put("phone", certifiPhone);
		
		return map;
			
	}
	
	
	// 인증 메일 전송
	@ResponseBody
	@PostMapping("/emailConfirm") 
	public Map emailConfirm(@RequestParam("email") String email) {
		boolean flag = false; // 중복된 메일 있음
		String authCode = null;
		
		Map map = new HashMap<>();
		
		System.out.println(email);
		MemberDto dto = service.getByEmail(email);
		
		if (dto != null && email.equals(dto.getEmail())) {
			System.out.println("중복 된 메일 존재");
			flag = true;			
		} else {
			authCode = emailService.joinEmail(email);
			flag = false;			
		}
		
		map.put("flag", flag);
		map.put("authCode", authCode);
		
		return map;
		
	}
	
	;
	// 닉네임 중복 체크
	@GetMapping("/nicknameConfirm")
	public Map nicknameConfirm(@RequestParam("nickname") String nickname) {
		boolean flag = false; // 중복된 닉네임 있음
		
		Map map = new HashMap<>();
		
		System.out.println(nickname);
		MemberDto dto = service.getByNickname(nickname);
		System.out.println(dto);
		
		if (dto != null && nickname.equals(dto.getName())) {
			flag = false;
		} else {
			flag = true;
		}
		
		map.put("flag", flag);
		System.out.println(flag);
		return map;

	}
	
	
	
	// 회원가입
	@PostMapping("/join")		
	public Map join(MemberDto dto) {
		
		System.out.println(dto);
		
		Map map = new HashMap();
		
		String userNum = service.generateRandomUserNum(dto.getSnsType()); 	// 회원번호 난수생성
		String billingKey = "0";	// 빌링키 값 0 설정
		
		dto.setUserNum(userNum);
		dto.setBillingKey("0");
	
		MemberDto dto2 = service.save(dto);
		System.out.println(dto2);
		
		map.put("dto", dto2);
		
		return map;
	}
	
	
	// 로그인
	@PostMapping("/login")
	public Map login(String email, String pwd) {
		Map map = new HashMap();
		
		System.out.println(email + "/" + pwd);
		
		boolean flag = false;
		MemberDto dto = service.getByEmail(email);
		ArrayList<MemberDto> dto2 = service.getAllUser();
		
		System.out.println(dto);
		System.out.println(dto2);
		
		if (dto != null && pwd.equals(dto.getPwd())) {
			String token = tokenprovider.generateJwtToken(dto);
			flag = true;
			map.put("token", token);
		}
		map.put("flag", flag);
		System.out.println(flag);
		return map;

	}

	// 내 정보 수정: 비밀번호, 이메일(인증), 전화번호(본인인증), 닉네임, 프로필 이미지

}
