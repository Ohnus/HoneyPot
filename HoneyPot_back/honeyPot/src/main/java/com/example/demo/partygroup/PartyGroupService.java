package com.example.demo.partygroup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.hostboard.HostBoard;
import com.example.demo.member.Member;

@Service
public class PartyGroupService {

	@Autowired
	private PartyGroupDao dao;

	//추가 및 수정 
	//1. 참여하기 버튼 누를 때 들어와있음 
	//2. start check 바꿀 때 수정 (근데 이건 따로 dao 만들어야 할 것 같음) 
	public PartyGroupDto save(PartyGroupDto dto) {
		PartyGroup pg = dao
				.save(new PartyGroup(dto.getGroupNum(), dto.getBoardNum(), dto.getUserNum(), dto.getStartCheck()));
	return new PartyGroupDto(pg.getGroupNum(),pg.getBoardNum(),pg.getUserNum(),pg.getStartCheck());
	}

	// 삭제
	// 구독 시작일 3일전까지는 취소 가능 취소하면 삭제 된다. 
	public void delete(int groupNum) {
		dao.deleteById(groupNum);
	}

	//인당 리스트 
	public ArrayList<PartyGroupDto> getUserList(Member userNum){
	ArrayList<PartyGroup> list = dao.findByUserNum(userNum);
	ArrayList<PartyGroupDto> list2 = new ArrayList<PartyGroupDto>();
	for(PartyGroup vo : list) {
		list2.add(new PartyGroupDto(vo.getGroupNum(),vo.getBoardNum(),vo.getUserNum(),vo.getStartCheck()));
	}
	return list2;
	}
	
	
	public void editStartTo1(int boardNum) {
		dao.updateStartCheckTo1(boardNum);
	}
	
	public void editStartTo2(int boardNum, int startCheck) {
		dao.updateStartCheckTo2(boardNum,startCheck);
	}
	
	public void editStartTo3(int boardNum) {
		dao.updateStartCheckTo3(boardNum);
	}
	
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
	public ArrayList<PartyGroupDto> findByStartCheck(int startCheck) {
		ArrayList<PartyGroup> list = dao.selectStartCheck(startCheck);
		ArrayList<PartyGroupDto> list2 = new ArrayList<PartyGroupDto>();
		for(PartyGroup vo : list) {
			list2.add(new PartyGroupDto(vo.getGroupNum(),vo.getBoardNum(),vo.getUserNum(),vo.getStartCheck()));
		}
		return list2;
		}
}
