package com.example.demo.partygroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
