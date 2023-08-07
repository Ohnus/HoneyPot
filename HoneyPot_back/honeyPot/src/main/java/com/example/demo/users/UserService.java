package com.example.demo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao dao;
	
	// 회원가입
	public String save(UserDto dto) {
		User entity = dao.save(new User(dto.getUserNum(), dto.getEmail(), dto.getPwd(), dto.getName(), dto.getNickname(),
				dto.getPhone(), dto.getSnsType(), dto.getBankCode(), dto.getBankAcc(), dto.getProfile(), dto.getBillingKey()));
		
		return entity.getUserNum();		
	}
	
	// 수정
	public UserDto edit(UserDto dto) {
		User entity = dao.save(new User(dto.getUserNum(), dto.getEmail(), dto.getPwd(), dto.getName(), dto.getNickname(),
				dto.getPhone(), dto.getSnsType(), dto.getBankCode(), dto.getBankAcc(), dto.getProfile(), dto.getBillingKey()));
		
		return new UserDto(entity.getUserNum(), entity.getEmail(), entity.getPwd(), entity.getName(), entity.getNickname(),
				entity.getPhone(), entity.getSnsType(), entity.getBankCode(), entity.getBankAcc(), entity.getProfile(), entity.getBillingKey(), null);
	}
	
	// 내 정보 확인
	public UserDto getUser(String userNum) {
		User entity = dao.findById(userNum).orElse(null);
		UserDto dto = null;
		
		if(entity == null) {
			return dto;
		} else {
			dto = new UserDto(entity.getUserNum(), entity.getEmail(), entity.getPwd(), entity.getName(), entity.getNickname(),
					entity.getPhone(), entity.getSnsType(), entity.getBankCode(), entity.getBankAcc(), entity.getProfile(), entity.getBillingKey(), null);
			return dto;
		}
	}
	
	// 탈퇴
	public void delUser(String userNum) {
		dao.deleteById(userNum);
	}

}
