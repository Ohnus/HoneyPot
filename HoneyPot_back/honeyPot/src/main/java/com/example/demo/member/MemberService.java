package com.example.demo.member;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	@Autowired
	private MemberDao dao;
	
	// 회원번호 난수 생성
	public String generateRandomUserNum(int snsType) {
		Random random = new Random();
		
		String key = "";
		
		switch (snsType) {
			case 0:				// 허니팟 계정 가입
				key = "HNP";
				break;
			case 1:				// 카카오 계정 가입
				key = "KKO";
				break;
			case 2:				// 네이버 계정 가입
				key = "NAV";
				break;
		}
		
		for (int i=0; i<7; i++) {	// 알파벳 + 7자리 숫자
			int numIndex = random.nextInt(10);
			key += numIndex;
		}
		
		return key;
	}
	
	
	// 회원가입
//	public String save(MemberDto dto) {
//		Member entity = dao.save(new Member(dto.getUserNum(), dto.getEmail(), dto.getPwd(), dto.getName(), dto.getNickname(),
//				dto.getPhone(), dto.getSnsType(), dto.getBankCode(), dto.getBankAcc(), dto.getProfile(), dto.getBillingKey()));
//		
//		return entity.getUserNum();		
//	}
	
	
	// 회원가입 및 수정
	public MemberDto save(MemberDto dto) {
		Member entity = dao.save(new Member(dto.getUserNum(), dto.getEmail(), dto.getPwd(), dto.getName(), dto.getNickname(),
				dto.getPhone(), dto.getSnsType(), dto.getBankCode(), dto.getBankAcc(), dto.getProfile(), dto.getBillingKey()));
		
		return new MemberDto(entity.getUserNum(), entity.getEmail(), entity.getPwd(), entity.getName(), entity.getNickname(),
				entity.getPhone(), entity.getSnsType(), entity.getBankCode(), entity.getBankAcc(), entity.getProfile(), entity.getBillingKey(), null);
	}
	
	
	// 로그인, 내 정보 확인
	public MemberDto getUser(String email) {
		Member entity = dao.findById(email).orElse(null);
		MemberDto dto = null;
		
		if(entity == null) {
			return dto;
		} else {
			dto = new MemberDto(entity.getUserNum(), entity.getEmail(), entity.getPwd(), entity.getName(), entity.getNickname(),
					entity.getPhone(), entity.getSnsType(), entity.getBankCode(), entity.getBankAcc(), entity.getProfile(), entity.getBillingKey(), null);
			return dto;
		}
	}
	
		
	public void delUser(String email) {
		dao.deleteById(email);
	}

}
