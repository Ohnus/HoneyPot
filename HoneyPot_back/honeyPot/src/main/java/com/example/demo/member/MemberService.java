package com.example.demo.member;

import java.util.ArrayList;
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
	

	// 회원가입 및 수정
	public MemberDto save(MemberDto dto) {
		Member entity = dao.save(new Member(dto.getUserNum(), dto.getEmail(), dto.getPwd(), dto.getName(), dto.getNickname(),
				dto.getPhone(), dto.getSnsType(), dto.getBankCode(), dto.getBankAcc(), dto.getProfile(), dto.getBillingKey()));
		
		return new MemberDto(entity.getUserNum(), entity.getEmail(), entity.getPwd(), entity.getName(), entity.getNickname(),
				entity.getPhone(), entity.getSnsType(), entity.getBankCode(), entity.getBankAcc(), entity.getProfile(), entity.getBillingKey(), null);
	}
		
	
	// 전체 검색
	public ArrayList<MemberDto> getAllUser() {
		ArrayList<Member> list = (ArrayList<Member>) dao.findAll();
		ArrayList<MemberDto> list2 = new ArrayList<MemberDto>();
		for(Member entity : list) {
			list2.add((new MemberDto(entity.getUserNum(), entity.getEmail(), entity.getPwd(), entity.getName(), entity.getNickname(),
					entity.getPhone(), entity.getSnsType(), entity.getBankCode(), entity.getBankAcc(), entity.getProfile(), entity.getBillingKey(), null)));
		}
		return list2;
	}
	
	
	// 유저번호로 검색
	public MemberDto getByUserNum(String userNum) {
		Member entity = dao.findById(userNum).orElse(null);
		MemberDto dto = null;
		
		if(entity == null) {
			return dto;
		} else {
			dto = new MemberDto(entity.getUserNum(), entity.getEmail(), entity.getPwd(), entity.getName(), entity.getNickname(),
					entity.getPhone(), entity.getSnsType(), entity.getBankCode(), entity.getBankAcc(), entity.getProfile(), entity.getBillingKey(), null);
			return dto;
		}
	}
	
	
	// 로그인, 내 정보 확인
	public MemberDto getByEmail(String email) {
		Member entity = dao.findByEmail(email);
		MemberDto dto = null;
		
		if(entity == null) {
			return dto;
		} else {
			dto = new MemberDto(entity.getUserNum(), entity.getEmail(), entity.getPwd(), entity.getName(), entity.getNickname(),
					entity.getPhone(), entity.getSnsType(), entity.getBankCode(), entity.getBankAcc(), entity.getProfile(), entity.getBillingKey(), null);
			return dto;
		}
	}
	
	
	// 닉네임 검색
	public MemberDto getByNickname(String nickname) {
		Member entity = dao.findByNickname (nickname);
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
