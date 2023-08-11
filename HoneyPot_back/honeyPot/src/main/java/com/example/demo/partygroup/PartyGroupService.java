package com.example.demo.partygroup;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.hostboard.HostBoard;
import com.example.demo.hostboard.HostBoardDto;
import com.example.demo.hostboard.HostBoardService;
import com.example.demo.member.Member;

@Service
public class PartyGroupService {

	@Autowired
	private PartyGroupDao dao;
	
	@Autowired
	private HostBoardService HBService; 

	//추가
	//1. 참여하기 버튼 누를 때 들어와있음 
	public PartyGroupDto save(PartyGroupDto dto) {
		PartyGroup pg = dao
				.save(new PartyGroup(dto.getGroupNum(), dto.getBoardNum(), dto.getUserNum(), dto.getStartCheck()));
	return new PartyGroupDto(pg.getGroupNum(),pg.getBoardNum(),pg.getUserNum(),pg.getStartCheck());
	}

	// 삭제
	public void delete(int groupNum) {
		dao.deleteById(groupNum);
	}

	//인당 리스트 -> 마이페이지에서 볼 것임  
	public ArrayList<PartyGroupDto> getUserList(Member userNum){
	ArrayList<PartyGroup> list = dao.findByUserNum(userNum);
	ArrayList<PartyGroupDto> list2 = new ArrayList<PartyGroupDto>();
	for(PartyGroup vo : list) {
		list2.add(new PartyGroupDto(vo.getGroupNum(),vo.getBoardNum(),vo.getUserNum(),vo.getStartCheck()));
	}
	return list2;
	}
	
	//구독이 시작되면 1로 바꿔줄 메서드 
	public void editStartTo1(int boardNum) {
		dao.updateStartCheckTo1(boardNum);
	}
	
	//구독이 종료가 되면 1로 바꿔줄 메서드 
	public void editStartTo2(int boardNum) {
		dao.updateStartCheckTo2(boardNum);
	}
	
	//걍 탈주자를 위한 메서드 
	public void editStartTo3(int boardNum) {
		dao.updateStartCheckTo3(boardNum);
	}
	
	//중간 탈주자를 위한 메서드 
	public Map editStartTo4(int boardNum) {
		Map map = new HashMap();
		boolean flag = true; // treu면 중간탈주가능합니다 
		HostBoardDto dto = HBService.getBoard(boardNum);//글 번호로 찾아 
		LocalDate today = LocalDate.now();
		LocalDate endDate = dto.getSubEnd();
		 long daysUntilEnd = ChronoUnit.DAYS.between(today, endDate);
		if(daysUntilEnd <= 30)  { //30일 이하로 남이 있으면 중간탈주 불가 
			flag = false; 
			map.put("flag", flag);
			} else { //30일 초과로 남아 있으면 중간 탈주 가능합니다 
				map.put("flag", flag);
				dao.updateStartCheckTo4(boardNum);
			}
		return map;
	}
	
	//일치하는 게시판 번호를 가진 행을 지울 예정
	public void delByBoardNum(int boardNum) {
		dao.deleteByBoardNum(boardNum);
	}
	
	//게시판 글 몇개인가 검색해서 숫자로 리턴  
	public int findByBoardNum(HostBoard boardNum) {
		ArrayList<PartyGroup> list = dao.findByBoardNum(boardNum);
		 return list.size();
	}
	
	//PK 아이디로 찾기 
	public PartyGroupDto finByGroupNum(int groupNum) {
		PartyGroup vo = dao.findById(groupNum).orElse(null);
		if (vo ==null) {
			return null;
		}
		return new PartyGroupDto(vo.getGroupNum(),vo.getBoardNum(),vo.getUserNum(),vo.getStartCheck());
	}
	
	//startcheck 로 검색 
	public ArrayList<PartyGroupDto> findByStartCheck(int boardNum, int startCheck) {
		ArrayList<PartyGroup> list = dao.selectStartCheck(boardNum,startCheck);
		ArrayList<PartyGroupDto> list2 = new ArrayList<PartyGroupDto>();
		for(PartyGroup vo : list) {
			list2.add(new PartyGroupDto(vo.getGroupNum(),vo.getBoardNum(),vo.getUserNum(),vo.getStartCheck()));
		}
		return list2;
		}
	
	//탈주자가 생겨서 인원이 비었을 때 진행 할 메서드 
	
}
