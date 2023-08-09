package com.example.demo.payment;

import java.time.LocalDate;

import com.example.demo.hostboard.HostBoard;
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
public class PaymentDto {
	
	private String orderNum;
	private Member userNum;
	private HostBoard boardNum;
	private int payInstallment;
	private long finalInstallment;
	private LocalDate recurringDay;
	private LocalDate payDate;
	private int totalPayment;
	private int deposit;
	private int monthPrice;
	private int commission;
	private String history;
	private String ottType;
}
