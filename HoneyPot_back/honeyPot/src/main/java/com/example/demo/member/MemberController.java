package com.example.demo.member;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
		boolean flag = false; // 중복된 메일 없음
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
	
	
	// 이메일 중복 체크 (sns 가입 시 비교)
	@GetMapping("/emailCheck")
	public Map emailCheck(@RequestParam("email") String email) {
		boolean flag = false; // 중복된 이메일 없음
		
		Map map = new HashMap<>();
		
		System.out.println(email);
		MemberDto dto = service.getByEmail(email);
		
		if(dto != null && email.equals(dto.getEmail())) {
			System.out.println("중복 된 메일 존재");
			flag = true;
		} else {
			flag = false;
		}
		map.put("flag", flag);
		
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
	
	// 내 정보 불러오기
	@GetMapping("/edit/{userNum}")
	public Map getInfo(@PathVariable("userNum") String userNum) {
		
		Map map = new HashMap();
		
		System.out.println(userNum);

		MemberDto dto = service.getByUserNum(userNum);
		System.out.println(dto);
		
		// 기존 정보 가져오기
		map.put("name", dto.getName());
		map.put("phone", dto.getPhone());
		map.put("email", dto.getEmail());
		map.put("pwd", dto.getPwd());
		map.put("nickname", dto.getNickname());
		map.put("profile", dto.getProfile());
		map.put("bankCode", dto.getBankCode());
		map.put("bankAcc", dto.getBankAcc());
		
		// 허니팟 계정인지 판단, 아니면 메일 수정 못하게 클라이언트 단에서 막음
		boolean hnpAccount = false;
		
		int snsType = dto.getSnsType();
		
		if (snsType == 0) {
			hnpAccount = true;
		}
		
		map.put("hnpAccount", hnpAccount);
		
		return map;
	}
	
	
	// 프로필 이미지 수정
	@PostMapping("/editImg/{userNum}")
	public Map editImg(@PathVariable("userNum") String userNum, @RequestParam("file") MultipartFile f, MemberDto dto) {
		Map map = new HashMap<>();
		
		String usernum = dto.getUserNum();
		File dir = new File(path + "/" + usernum);
		
		if (!dir.exists()) {
			dir.mkdir();
		}

		
		String fname = f.getOriginalFilename();
		String newpath = path + usernum + "/" + fname;
		File newfile = new File(newpath);
		
		try {
			f.transferTo(newfile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dto.setProfile(newpath);
		service.save(dto);
		
		map.put("dto", dto);
		
		return map;
	}
	

	// 내 정보 수정: 비밀번호, 이메일(인증), 닉네임, 프로필 이미지
	@PostMapping("/edit/{userNum}")
	public Map editInfo(@PathVariable("userNum") String userNum, MemberDto updatedDto) {
	
		System.out.println("신규이메일: " + updatedDto.getEmail());
		System.out.println("신규비밀번호: " + updatedDto.getPwd());
		System.out.println("신규닉네임: " + updatedDto.getNickname());
		
		MemberDto dto = service.getByUserNum(userNum);
		
		System.out.println("기존이메일: " + dto.getEmail());
		System.out.println("기존비밀번호: " + dto.getPwd());
		System.out.println("기존닉네임: " + dto.getNickname());
		
		// 새 정보 담기
		dto.setEmail(updatedDto.getEmail());
		dto.setPwd(updatedDto.getPwd());
		dto.setNickname(updatedDto.getNickname());
		
		Map map = new HashMap();
		
		dto = service.save(dto);
		System.out.println("업데이트된 정보: " + dto);
		
		map.put("dto", dto);
		
		return map;
	}
	
	
	// 예금주 조회
	@GetMapping("/certifications/checkAccount")
	public Map CheckAccount(@RequestParam("bankCode") String bankCode, @RequestParam("bankAcc") String bankAcc) {
		Map map = new HashMap<>();
	
		System.out.println("왔다");
		System.out.println(bankCode + " / " + bankAcc);
		map = certificationService.getAccessToken1(bankCode, bankAcc);
	
		String bankHolderInfo = (String) map.get("bankHolderInfo");
		
		Object errorObj = map.get("error");
		if (errorObj instanceof String) {
		    String errorStr = (String) errorObj;
		    int error1 = Integer.parseInt(errorStr);
		    map.put("errormsg", String.valueOf(error1));
		} 
		
		map.put("bankHolderInfo", bankHolderInfo);

		return map;
	}
	
	
	// 계좌정보 수정
	@PostMapping("/editBankInfo/{userNum}")
	public Map editBankInfo(@PathVariable("userNum") String userNum, MemberDto updatedDto) {
	
		System.out.println("신규은행코드: " + updatedDto.getBankCode());
		System.out.println("신규계좌번호: " + updatedDto.getBankAcc());
		
		MemberDto dto = service.getByUserNum(userNum);
		
		System.out.println("기존은행코드: " + dto.getBankCode());
		System.out.println("기존계좌번호: " + dto.getBankAcc());
		
		// 새 정보 담기
		dto.setBankCode(updatedDto.getBankCode());
		dto.setBankAcc(updatedDto.getBankAcc());

		Map map = new HashMap();
		
		dto = service.save(dto);
		System.out.println("업데이트된 정보: " + dto);
		
		map.put("dto", dto);
		
		return map;
	}
	
	
	// 로그인
	@PostMapping("/login")
	public Map login(String email, String pwd) {
		Map map = new HashMap();
		
		System.out.println(email + "/" + pwd);
		
		boolean flag = false;
		
		MemberDto dto = service.getByEmail(email);
		String userNum = dto.getUserNum();
		
		System.out.println(dto);
		
		if (dto != null && pwd.equals(dto.getPwd())) {
			String token = tokenprovider.generateJwtToken(dto); // 토큰 발급
			flag = true;
			map.put("token", token);
		}
		map.put("flag", flag);
		map.put("email", dto.getEmail());
		map.put("userNum", userNum);
		
		System.out.println(flag);
		System.out.println(email);
		System.out.println(userNum);
		
		return map;
	}
	
	// 로그아웃
	
	// 회원탈퇴
	
	
}
