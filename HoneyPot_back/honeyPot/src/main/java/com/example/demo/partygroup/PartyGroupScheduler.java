package com.example.demo.partygroup;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.hostboard.HostBoard;
import com.example.demo.hostboard.HostBoardDto;
import com.example.demo.hostboard.HostBoardService;

@EnableScheduling
//@SpringBootApplication
@Component
public class PartyGroupScheduler {

	@Autowired
	private HostBoardService HBService;
	@Autowired
	private PartyGroupService PGService;

	@Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 진행
	public void UpdateStartcheckToOne() {
		LocalDate today = LocalDate.now(); 
		ArrayList<HostBoardDto> hostBoards = HBService.getAll(); //리스트를 전체 다 뽑아 
		for (HostBoardDto dto : hostBoards) { //배열 만큼 돌아 
			HostBoard vo = new HostBoard(); //새로운 객체생성
			vo.setBoardNum(dto.getBoardNum()); // 게시판 번호를 vo에도 설정 
			if (dto.getSubStart().isEqual(today)) { // 구독일이 오늘 날짜라면
				int currentParticipants = PGService.findByBoardNum(vo); // 몇 명이 구독신청을 했나 찾고
				if (currentParticipants >= dto.getMinPpl()) { // 찾은 숫자가 최소인원보다 크면
					PGService.editStartTo1(vo.getBoardNum()); // StartCheck를 1로 바꿔
				} else { // 최소 인원을 채우지 못했다면
					PGService.delByBoardNum(vo.getBoardNum());
				}
			} else if(dto.getSubEnd().isEqual(today)) { //종료일이 오늘 날짜라면
				PGService.editStartTo2(vo.getBoardNum(),1);
			} 
		}
	}

	
}