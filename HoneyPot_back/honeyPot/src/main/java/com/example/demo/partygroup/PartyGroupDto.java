package com.example.demo.partygroup;

import com.example.demo.automatching.AutoMatching;
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
public class PartyGroupDto {

	private int groupNum;
	private HostBoard boardNum;
	private Member userNum;
	private int startCheck;
	private AutoMatching matchingNum;

}
