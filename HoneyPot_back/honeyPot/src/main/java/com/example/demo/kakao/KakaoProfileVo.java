package com.example.demo.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfileVo {
	
public Long id;
public String connected_at;
public Kakaoaccount kakao_account;

//내부 클래스
@Data
class Kakaoaccount {
	public Boolean has_email;
	public Boolean email_needs_agreement;
	public Boolean is_email_valid;
	public Boolean is_email_verified;
	public String email;
}

}

