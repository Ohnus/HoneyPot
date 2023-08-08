package com.example.demo.hostBoard;

import java.time.LocalDate;

import com.example.demo.ott.Ott;
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
public class HostBoardDto {
	
	private int boardNum;
	private User userNum; 
	private Ott type; 
	private int monthPrice;
	private String ottAcct; 
	private String ottPwd;
	private int minPpl; 
	private int maxPpl;
	private LocalDate subStart; 
	private LocalDate subEnd; 
	private long month;
	private int ing;

}
