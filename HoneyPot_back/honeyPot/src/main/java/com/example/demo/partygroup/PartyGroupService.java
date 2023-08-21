package com.example.demo.partygroup;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	// 추가
	// 1. 참여하기 버튼 누를 때 들어와있음
	public PartyGroupDto save(PartyGroupDto dto) {
		PartyGroup pg = dao
				.save(new PartyGroup(dto.getGroupNum(), dto.getBoardNum(), dto.getUserNum(), dto.getStartCheck(),dto.getMatchingNum()));
		return new PartyGroupDto(pg.getGroupNum(), pg.getBoardNum(), pg.getUserNum(), pg.getStartCheck(),pg.getMatchingNum());
	}
	
	
	// 삭제
	public void delete(int groupNum) {
		dao.deleteById(groupNum);
	}

	// 인당 리스트 -> 마이페이지에서 볼 것임
	public ArrayList<PartyGroupDto> getUserList(Member userNum) {
		ArrayList<PartyGroup> list = dao.findByUserNum(userNum);
		ArrayList<PartyGroupDto> list2 = new ArrayList<PartyGroupDto>();
		for (PartyGroup vo : list) {
			list2.add(new PartyGroupDto(vo.getGroupNum(), vo.getBoardNum(), vo.getUserNum(), vo.getStartCheck(),vo.getMatchingNum()));
		}
		return list2;
	}
	
	//구독 중복 있나 보려고 하는 메서드 
	public int checkPartyGroup(int boardNum, String userNum) {
		 ArrayList<PartyGroup> list = (ArrayList<PartyGroup>) dao.checkPartyGroup(boardNum, userNum);
			ArrayList<PartyGroupDto> list2 = new ArrayList<PartyGroupDto>();
			for (PartyGroup vo : list) {
				list2.add(new PartyGroupDto(vo.getGroupNum(), vo.getBoardNum(), vo.getUserNum(), vo.getStartCheck(),vo.getMatchingNum()));
			}
		 int isparticipant = list2.size(); //사이즈가 1 아님 0일 것임 
		 return isparticipant;
	}

	// 구독이 시작되면 1로 바꿔줄 메서드
	public void editStartTo1(int boardNum) {
		dao.updateStartCheckTo1(boardNum); // 구독이 시작되어 1로 바꿈

		// 게시판에서 글이 안보이게 할지 말지 결정을 해야 하는데
		// 그렇게 하기 위해서는 게시판 maxppl의 수와 partygroup에 몇명이 있는지를 또 비교 해야함

		// hostBoard의 게시판의 maxppl은
		HostBoardDto dto = HBService.getBoard(boardNum);
		System.out.println("%% dto뿌립니다" + dto);
		// partygroup에 몇명있는지는
		int partyGroupCount = findByBoardNumUsingInt(dto.getBoardNum());
		System.out.println("!!!!몇명이냐면!!! " + partyGroupCount);
		
		if (dto.getMaxPpl() == partyGroupCount) {
			// 최대인원까지 꽉참 -> 리스트에서 보여줄 필요 없음
			HBService.changIngToOne(boardNum);
		System.out.println("~~~~~~~~~~~~~~~~~성공일껄");
		}

	}

	// 구독이 종료가 되면 1로 바꿔줄 메서드
	public void editStartTo2(int boardNum) {
		dao.updateStartCheckTo2(boardNum);
	}

	// 탈주자를 위한 메서드
	public void editStartTo3(int boardNum,String userNum) {
		dao.updateStartCheckTo3(boardNum, userNum);
	}

	// 중간 탈주자를 위한 메서드
	public Map editStartTo4(int boardNum, String userNum) {
		Map map = new HashMap();
		boolean flag = true; // true면 중간탈주가능합니다
		HostBoardDto dto = HBService.getBoard(boardNum);// 글 번호로 찾아
		LocalDate today = LocalDate.now();
		LocalDate endDate = dto.getSubEnd();
		long daysUntilEnd = ChronoUnit.DAYS.between(today, endDate);
		if (daysUntilEnd <= 30) { // 30일 이하로 남이 있으면 중간탈주 불가
			flag = false;
			map.put("flag", flag);
		} else { // 30일 초과로 남아 있으면 중간 탈주 가능합니다
			map.put("flag", flag);
			dao.updateStartCheckTo4(boardNum,userNum);
			
		}
		return map;
	}

	// 일치하는 게시판 번호를 가진 행을 지울 예정
	public void delByBoardNum(int boardNum) {
		dao.deleteByBoardNum(boardNum);
		HBService.delete(boardNum);
		System.out.println("###$%안뇽하세요? 자바에요? 저두 성공~~");
	}


	// 게시판 글 몇개인가 검색해서 숫자로 리턴
	public int findByBoardNum(HostBoard boardNum) {
		ArrayList<PartyGroup> list = dao.findByBoardNum(boardNum);
		return list.size();
	}

	// int 글 번호로 찾기 
	public int findByBoardNumUsingInt(int boardNum) {
		ArrayList<PartyGroup> list = dao.findByBoardNumUsingInt(boardNum);
		return list.size();
	}

	// PK 아이디로 찾기
	public PartyGroupDto finByGroupNum(int groupNum) {
		PartyGroup vo = dao.findById(groupNum).orElse(null);
		if (vo == null) {
			return null;
		}
		return new PartyGroupDto(vo.getGroupNum(), vo.getBoardNum(), vo.getUserNum(), vo.getStartCheck(),vo.getMatchingNum());
	}

	// startcheck 로 검색
	public ArrayList<PartyGroupDto> findByStartCheck(int boardNum, int startCheck) {
		ArrayList<PartyGroup> list = dao.selectStartCheck(boardNum, startCheck);
		ArrayList<PartyGroupDto> list2 = new ArrayList<PartyGroupDto>();
		for (PartyGroup vo : list) {
			list2.add(new PartyGroupDto(vo.getGroupNum(), vo.getBoardNum(), vo.getUserNum(), vo.getStartCheck(),vo.getMatchingNum()));
		}
		return list2;
	}
	
	// startcheck 로 검색
		public int findUsingStartCheck(int boardNum, int startCheck) {
			ArrayList<PartyGroup> list = dao.selectStartCheck(boardNum, startCheck);
			
			return list.size();
		}
	//추가 된게 있나 확인 
	public boolean findByMatchingNum(int matchingNum) {
		
		    List<PartyGroup> partyGroups = dao.findByMatchingNum(matchingNum);
		    
		    return partyGroups.isEmpty();
	//비어있어야 partyGroup에 추가 할 수 있음으로 비어있는거이 true 여야함 
		    //매칭 넘이 있으면 false 를 반환 -> 추가하지못함으로 
	
	}

	
}
