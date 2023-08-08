package com.example.demo.pending;

import java.time.LocalDate;

import com.example.demo.hostBoard.HostBoard;
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
public class PendingDto {

	private int pendingNum;
	private HostBoard boardNum;
	private User userNum;
	private LocalDate patDate;
	private int amount;
	private int msg;
}
