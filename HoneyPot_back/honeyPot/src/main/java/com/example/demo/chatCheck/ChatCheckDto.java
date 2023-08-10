package com.example.demo.chatCheck;

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
public class ChatCheckDto {
	
	private int checkNum; 
	private int chatNum; 
	private int boardNum; 
	private String toReciever;
	private int read; 

}
