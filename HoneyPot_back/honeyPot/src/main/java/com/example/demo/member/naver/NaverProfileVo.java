package com.example.demo.member.naver;

import lombok.Data;

@Data
public class NaverProfileVo {
	
	private String resultcode;
	private String message;
	private response response;
	
	@Data
	public class response {
		private String id;
		private String nickname;
		private String name;
	    private String email;
        private String gender;
        private String age;
        private String birthday;
        private String profile_image;
        private String birthyear;
	}

	@Override
	public String toString() {
		return "NaverProfileVo [response=" + response + "]";
	}

}
