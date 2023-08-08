package com.example.demo.cash;

import java.time.LocalDate;

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
public class CashDto {
	
	private int cashNum;
	private User userNum;
	private LocalDate addDate;
	private String msg;
	private int amount;
}
