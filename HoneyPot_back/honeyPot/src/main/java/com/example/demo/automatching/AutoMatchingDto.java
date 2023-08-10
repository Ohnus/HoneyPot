package com.example.demo.automatching;

import java.time.LocalDate;

import com.example.demo.member.Member;
import com.example.demo.ott.Ott;

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
public class AutoMatchingDto {
	
	private int matchingNum;
	private Member userNum;
	private Ott type;
	private LocalDate subStart;
	private int exDate;
	private int monthMin;
	private int monthMax;

}
