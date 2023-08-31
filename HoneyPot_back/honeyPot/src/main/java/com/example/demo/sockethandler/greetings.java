package com.example.demo.sockethandler;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class greetings {
	String type; // enter:접속, exit :나감
	String id;
	int boardNum; // 현재 접속한 채팅방

}
