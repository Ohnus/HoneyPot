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
	
	@Value("${spring.servlet.multipart.location}")
	private String path;	// C:/honeypot/
	
	
	// 본인인증 통한 회원가입 이력 여부 확인
	@GetMapping("/certifications/redirect")
	public Map handleRedirect(@RequestParam("imp_uid") String impUid) {
		boolean certified = false;		// 중복된 핸드폰번호 + 이름 없음
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
		

		if (dtoList == null) {
			System.out.println("회원가입 하자!");	// 데이터가 없을 때 이거 작동을 안함.. 왤까..
		} else {
			for (MemberDto list : dtoList) {	// 본인인증된 이름+핸드폰 값과 DB 데이터를 비교하여 회원가입 여부 판단
				if (list.getPhone().equals(certifiPhone) && list.getName().equals(certifiName)) {
					System.out.println("이름 + 핸드폰 값 중복");
					duplicated1 = true;
					break;
				} else if (list.getPhone().equals(certifiPhone) && !list.getName().equals(certifiName)) {
					System.out.println("핸드폰 값만 중복");
					duplicated2 = true;
					break;
				} else {
					System.out.println("회원가입 하자!");
					certified = true;
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
			
	
	// 회원가입: 본인인증(이름+핸드폰번호) 값으로 가입 이력 여부 확인
//	@PostMapping("")		
//	public Map join(MemberDto dto, int snsType) {
//		Map map = new HashMap();
//		
//		String checkName = dto.getName();
//		String checkPhone = dto.getPhone();
//		
//		System.out.println(checkName + checkPhone);
//
//		MemberDto joinCheck = service.getByUserNum(checkPhone);	// 핸드폰 번호로 유저 정보 검색
//		
//		String checkName2 = joinCheck.getName();
//		String checkPhone2 = joinCheck.getPhone();
//		String id = joinCheck.getEmail();
//		
//		System.out.println(checkName2 + checkPhone2 + id);
//		
//		
//		if (checkPhone2 != null && checkName.equals(checkName2)) {	// 핸드폰번호 존재 여부 + 해당 핸드폰번호의 소유주와 이름이 일치하는지 확인
//			map.put("msg", "해당 정보로 이미 가입된 상태입니다. \n" + id + "로 로그인해주세요.");
//			
//		} else if (checkPhone2 != null) {
//			map.put("msg", "해당 핸드폰번호로 가입된 이름이 존재합니다. \n" + "고객센터로 문의해주세요.");
//			
//		} else {
//			String userNum = service.generateRandomUserNum(snsType);	// userNum 난수 생성
//			dto.setUserNum(userNum);									// 생성된 난수 dto에 저장
//					
//			MemberDto dto2 = service.save(dto);
//			System.out.println(dto2);
//			map.put("dto", dto2);
//		}
//		
//		return map;
//	}
//	
	
	// 로그인
	@PostMapping("/login")
	public Map login(String email, String pwd) {
		Map map = new HashMap();
		
		boolean flag = false;
		MemberDto dto = service.getByEmail(email);
		
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
