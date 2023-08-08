package com.example.demo.cash;

import java.time.LocalDate;

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
public class CashDto {
	
	private int cashNum;
	private Member userNum;
	private LocalDate addDate;
	private String msg;
	private int amount;
}
