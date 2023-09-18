package com.example.demo.member;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.partygroup.PartyGroupService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/members")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private CertificationService certificationService;
	
	@Autowired
	private PartyGroupService partyService;
	
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
	
	
	// 회원가입 인증메일
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
	
	// 아이디 찾기 인증메일
	@ResponseBody
	@PostMapping("/findIdEmailConfirm") 
	public Map findIdEmailConfirm(@RequestParam("email") String email) {
		String authCode = null;
			
		Map map = new HashMap<>();
			
		System.out.println(email);
		MemberDto dto = service.getByEmail(email);
			
		if (dto != null && email.equals(dto.getEmail())) {
			authCode = emailService.joinEmail(email);	
		}
			
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
		
		if (dto != null && nickname.equals(dto.getNickname())) {
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
		
		String userNum;
		
		if (dto.getSnsType() == 1) {
			userNum = service.generateRandomUserNum(dto.getSnsType(), dto.getUserNum()); // 카카오 회원번호
		} else {
			userNum = service.generateRandomUserNum(dto.getSnsType()); 	// 허니팟,네이버 회원번호 난수생성
		}
		
		String billingKey = "0";	// 빌링키 값 0 설정
		
		dto.setUserNum(userNum);
		dto.setBillingKey("0");
		
		File dir = new File(path + "/" + userNum);
		dir.mkdir();
		
		MemberDto dto2 = service.save(dto);
		System.out.println(dto2);
		
		map.put("dto", dto2);
		
		return map;
	}
	
	
	// 본인인증 아이디 찾기
	@GetMapping("/getId")
	public Map getIdByCertification(@RequestParam("name") String name, @RequestParam("phone") String phone) {
	
		System.out.println(name + " / " + phone);
		Map map = new HashMap<>();
		
		MemberDto dto = service.getByNameAndPhone(name, phone);
		System.out.println(dto);

		int snsType = dto.getSnsType();
		
		String snsValue = "";
		
		switch (snsType) {
			case 0:				// 허니팟 계정 가입
				snsValue = "허니팟";
				break;
			case 1:				// 카카오 계정 가입
				snsValue = "카카오";
				break;
			case 2:				// 네이버 계정 가입
				snsValue = "네이버";
				break;
		}
		
		map.put("userNum", dto.getUserNum());
		map.put("name", dto.getName());
		map.put("email", dto.getEmail());
		map.put("snsType", snsValue);
		
		return map;		
	}
	
	// 이메일 아이디 찾기
	@GetMapping("/getId2")
	public Map getIdByEmail(@RequestParam("name") String name, @RequestParam("email") String email) {

		System.out.println("넘어온 값: " + name + " / " + email);
		
		boolean flag = false;	// 입력값과 DB 일치여부

		Map map = new HashMap<>();
				
		MemberDto dto = service.getByNameAndEmail(name, email);
		
		if (dto == null) {
			flag = false;
		} else if (name.equals(dto.getName()) && email.equals(dto.getEmail())) {
			flag = true;
			
			int snsType = dto.getSnsType();
		
			String snsValue = "";
		
			switch (snsType) {
				case 0:				// 허니팟 계정 가입
					snsValue = "허니팟";
					break;
				case 1:				// 카카오 계정 가입
					snsValue = "카카오";
					break;
				case 2:				// 네이버 계정 가입
					snsValue = "네이버";
					break;
			}
	
			map.put("userNum", dto.getUserNum());
			map.put("name", dto.getName());
			map.put("email", dto.getEmail());
			map.put("snsType", snsValue);
			System.out.println(dto.getName() + dto.getEmail() + snsValue);

		}
		
		map.put("flag", flag);
		System.out.println(flag);
		
		return map;
	}

	
	
	// 내 정보 불러오기
	@GetMapping("/{userNum}")
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
		map.put("snsType", dto.getSnsType());
		
		// 허니팟 계정인지 판단, 아니면 메일 수정 못하게 클라이언트 단에서 막음
		boolean hnpAccount = false;
		
		int snsType = dto.getSnsType();
		
		if (snsType == 0) {
			hnpAccount = true;
		}
		
		map.put("hnpAccount", hnpAccount);
		
		return map;
	}
	
	
	// 프로필사진 파일 가져오기
	@GetMapping("/imgs/{userNum}") 
	public ResponseEntity<byte[]> read_img(@PathVariable("userNum") String userNum) {
		String fname = "";
		MemberDto dto = service.getByUserNum(userNum);
		fname = dto.getProfile();
		System.out.println(fname);

		ResponseEntity<byte[]> result = null; // 선언

		try {
			if (fname != null && fname.length() != 0) {
				fname = URLDecoder.decode(fname, "utf-8");
				File f = new File(fname);
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Type", Files.probeContentType(f.toPath()));
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 프로필 사진 삭제하기

	@DeleteMapping("/imgs/{userNum}")
	public Map removeImg(@PathVariable("userNum") String userNum){
		Map map = new HashMap();
		boolean flag = true;
		try {
			MemberDto dto = service.getByUserNum(userNum);
			String oldProfilePath = dto.getProfile();
			System.out.println(oldProfilePath);
			if(oldProfilePath != null && !oldProfilePath.isEmpty()) {
				File oldImgFile = new File(URLDecoder.decode(oldProfilePath, "utf-8"));
				System.out.println(oldImgFile);
				oldImgFile.delete();
				
				dto.setProfile(null);
				service.save(dto);
			}else {
				flag = false;
			}
			
		}catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}
	
	

	// 내 정보 수정: 비밀번호, 이메일(인증), 닉네임, 프로필 이미지
	@PostMapping("/edit/{userNum}")
	public Map editInfo(@PathVariable("userNum") String userNum, MultipartFile f, MemberDto updatedDto) {

		MemberDto dto = service.getByUserNum(userNum);

		String fname = null;
		
		if (f != null && !f.isEmpty()) {
			fname = f.getOriginalFilename();
			
			String newpath = path + dto.getUserNum() + "/" + fname;
			File newfile = new File(newpath);
			System.out.println("newpath: " + newpath);
			
			try {
				f.transferTo(newfile);
				dto.setProfile(newpath);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
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
	
	
	// 비밀번호만 수정 (비밀번호 찾기)
	@PostMapping("/editPwd/{userNum}")
	public Map editPwd(@PathVariable("userNum") String userNum, MemberDto updatedDto) {
		
		System.out.println("신규비밀번호: " + updatedDto.getPwd());
			
		MemberDto dto = service.getByUserNum(userNum);
			
		System.out.println("기존비밀번호: " + dto.getPwd());
			
		// 새 정보 담기
		dto.setPwd(updatedDto.getPwd());

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
		boolean account = false;
		
		MemberDto dto = service.getByEmail(email);
		System.out.println(dto);
		
		String userNum = dto.getUserNum();
		int snsType = dto.getSnsType();
		String billingKey = dto.getBillingKey();
		
		System.out.println(snsType);
		
		if (snsType == 0) {
			account = true;
			
			if (dto != null && pwd.equals(dto.getPwd())) {
				String token = tokenprovider.generateJwtToken(dto); // 토큰 발급
				flag = true;
				map.put("token", token);
			}
			
		} else {
			account = false;
		}
		
		map.put("flag", flag);
		map.put("account", account);
		map.put("email", dto.getEmail());
		map.put("userNum", userNum);
		map.put("snsType", snsType);
		map.put("billingKey", billingKey);
		
		return map;
	}
	
	// userNum (카카오 로그인 시 기존회원 확인)
	@GetMapping("/userNumCheck/{userNum}")
	public Map userNumCheck(@PathVariable("userNum") String userNum) {
		boolean flag = false; // 회원가입 안되어 있음
		
		System.out.println("userNum 회원가입 확인");
		Map map = new HashMap<>();
		
		System.out.println(userNum);
		MemberDto dto = service.getByUserNum(userNum);
		
		if(dto != null && userNum.equals(dto.getUserNum())) {
			System.out.println("이미 가입된 회원");
			flag =false;
		} else {
			flag = true;
		}
		map.put("flag", flag);
		map.put("dto", dto);
		return map;
	}
	
	
	// 네이버 가입 여부 확인
	@GetMapping("/naverCheck")
	public Map emailCheck(@RequestParam("email") String email) {
		boolean flag = false; // 중복된 네이버 아이디 없음
		boolean flag2 = false; // 중복된 메일 없음

		System.out.println("이메일 중복 검사");
		Map map = new HashMap<>();
		
		System.out.println(email);
		MemberDto dto = service.getByEmail(email);
		
		System.out.println(dto);
		
		if(dto != null && email.equals(dto.getEmail()) && dto.getSnsType() == 2) {
			System.out.println("중복 된 네이버 아이디 존재");
			flag = true;
		} else if (dto != null && email.equals(dto.getEmail())) {
			System.out.println("중복 된 이메일 존재");
			flag2 = true;
		} else {
			System.out.println("중복 된 아이디 & 이메일 없음");
			flag = false;
		}
		
		map.put("flag", flag);
		map.put("flag2", flag2);
		map.put("dto", dto);

		return map;
	}
	
	// 회원탈퇴
	@DeleteMapping("/{userNum}")
	public Map del(@PathVariable("userNum") String userNum, @RequestHeader(name="token", required=false) String token) {
	    boolean flag;             		// 탈퇴 요청자가 토큰 소지자와 일치하는지 true면 일치
	    boolean cashRemain=false;       // 캐시 남아있는지 true면 남아있음. false인 경우에만 탈퇴 가능
	    boolean partyRemain;      		// 파티 참여중인지 true면 참여중임. false인 경우에만 탈퇴 가능

	    Map map = new HashMap();
	    
	    if (token != null) {
	    	String tokenUserId = tokenprovider.getIdFromToken(token);	// 이메일 값 반환
	    	
	    	System.out.println(tokenUserId);
	    	
	    	MemberDto dto = service.getByEmail(tokenUserId);			// 이메일로 검색
	    	String tokenUserNum = dto.getUserNum();	    				// 이메일로 검색한 회원번호
	    	
	    	if(tokenUserNum.equals(userNum)) {							// 토큰 기준 회원번호와 세션 기준 회원번호가 일치하면
	    		flag = true;
  		
	    		partyRemain = partyService.outcheck(userNum);			// 파티 중인지 검색
	    		System.out.println("partyRemain: " + partyRemain);
	    		
	    		// 잔액 있는지 검색
	    		
	    		map.put("flag", flag);
	    	    map.put("partyRemain", partyRemain);
	    	    map.put("cashRemain", cashRemain);
	    		
	    		if (flag && !partyRemain && !cashRemain) {				// 토큰-세션 기준 회원번호 일치하고 파티 미참여 중이고 남은 캐시도 없으면 회원 탈퇴 진행   		
		    		System.out.println("탈퇴 완료");
	
	    		} else {
	    			System.out.println("탈퇴 진행 안함");
	    		}
	    	} else {
	    		flag = false;		// 토큰과 세션 기준 아이디가 일치하지 않음
	    		map.put("flag", flag);
	    	} 

	    }
	

	    return map;
	}
}
	    

