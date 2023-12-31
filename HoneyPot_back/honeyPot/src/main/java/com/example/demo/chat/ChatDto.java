package com.example.demo.chat;

import java.time.LocalDateTime;

import com.example.demo.member.Member;

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
public class ChatDto {
	
	private int chatNum;
	private int boardNum;
	private Member isFromSender;
	private String content;
	private LocalDateTime time;

}
