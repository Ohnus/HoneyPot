package com.example.demo.users;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
	
	private String userNum;
	private String email;
	private String pwd;
	private String name;
	private String nickname;
	private String phone;
	private int snsType;
	private int bankCode;
	private int bankAcc;
	private String profile;
	private String billingKey;
	private MultipartFile f;	

}
