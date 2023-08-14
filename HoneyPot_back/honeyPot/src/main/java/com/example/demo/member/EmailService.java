package com.example.demo.member;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	// 인증번호 및 임시비밀번호 생성
	public String createCode() {
		Random random = new Random();
		StringBuffer key = new StringBuffer();
		
		for (int i=0; i<8; i++) {
		
		int index = random.nextInt(4);
			
		switch (index) {
			case 0: 
				key.append((char) ((int) random.nextInt(26) + 97));
				break;
			case 1:
				key.append((char) ((int) random.nextInt(26) + 65));
				break;
			default:
				key.append(random.nextInt(9));
			}
		}
		return key.toString();
	}
	
	// 이메일 내용
	public String joinEmail(String email) {
		
		String authCode = createCode();
		String setFrom = "honeypot@honeypot.com";
		String toEmail = email;
		String title = "Honeypot 인증 메일입니다.";
		String content = "<h3>안녕하세요. Honeypot 입니다.</h3>" +
						 "<p>이메일 인증을 위한 인증번호는 " + authCode + " 입니다.</p>" +
						 "<p>해당 인증번호를 인증번호 확인 란에 기입하여 주세요.</p>";
		mailSend(setFrom, toEmail, title, content);	
		
		System.out.println("인증번호: " + authCode);
		return authCode;
	}
	
	// 이메일 전송
	public void mailSend(String setFrom, String toEmail, String title, String content) {
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toEmail);
			helper.setSubject(title);
			helper.setText(content, true); // true: html 형식으로 전송, 작성하지 않으면 텍스트로 전달
			mailSender.send(message);
		
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
