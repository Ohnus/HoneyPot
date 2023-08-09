package com.example.demo.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Member {
	
	@Id
	private String userNum;

	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String pwd;
	
	@Column(nullable=false)
	private String name;
	
	private String nickname;
	
	@Column(nullable=false)
	private String phone;
	
	@Column(nullable=false)
	private int snsType;	// 가입된 계정 타입: 허니팟 0 / 카카오 1 / 네이버 2
	
	private int bankCode;	// 은행 코드
	private int bankAcc;	// 계좌 번호
	private String profile;	// 프로필 사진
	
	@Column(nullable=false)
	private String billingKey = "0";	// 빌링키: 계정 당 하나만 발급되며(여러 개 카드 등록 불가) 디폴트 값은 0으로 둠
	
}
