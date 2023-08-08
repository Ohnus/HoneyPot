package com.example.demo.chatheader;


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
public class ChatHeaderDto {

	private int channelNum; 
	private int boardNum; 
	private String subject; 
	private Member host;
}
