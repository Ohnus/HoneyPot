package com.example.demo.chat;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import com.example.demo.users.User;


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
	
	private int messageNum;
	private int boardNum;
	private User isFromSender;
	private String content;
	private LocalDate time;

}
