package com.example.demo.withdrawl;

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
public class WithdrawlDto {

	private int withdrawlNum;
	private Member userNum;
	private LocalDate withdrawlDate; // 출금 날짜
	private int amount; // 출금액
}
