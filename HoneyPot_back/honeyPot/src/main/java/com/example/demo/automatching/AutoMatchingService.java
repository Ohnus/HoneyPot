package com.example.demo.automatching;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.hostboard.HostBoard;
import com.example.demo.hostboard.HostBoardDto;
import com.example.demo.hostboard.HostBoardService;
import com.example.demo.member.Member;
import com.example.demo.partygroup.PartyGroupDto;
import com.example.demo.partygroup.PartyGroupService;

@Service
public class AutoMatchingService {

	@Autowired
	private AutoMatchingDao dao;

	

	@Autowired
	private PartyGroupService PGService;

	@Autowired
	private HostBoardService HBService;

	// 추가와 수정
	public AutoMatchingDto save(AutoMatchingDto dto) {
		AutoMatching vo = dao.save(new AutoMatching(dto.getMatchingNum(), dto.getUserNum(), dto.getType(),
				dto.getStartDate(), dto.getExDate(), dto.getMonthMin(), dto.getMonthMax(), dto.getIsMatching()));
		return new AutoMatchingDto(vo.getMatchingNum(), vo.getUserNum(), vo.getType(), vo.getStartDate(),
				vo.getExDate(), vo.getMonthMin(), vo.getMonthMax(), vo.getIsMatching());
	}

	// 상세 내용 보기
	public AutoMatchingDto getBoard(int matchingNum) {
		AutoMatching vo = dao.findById(matchingNum).orElse(null);
		if (vo == null) {
			return null;
		}
		return new AutoMatchingDto(vo.getMatchingNum(), vo.getUserNum(), vo.getType(), vo.getStartDate(),
				vo.getExDate(), vo.getMonthMin(), vo.getMonthMax(), vo.getIsMatching());
	}

	// 사용자가 직접 삭제
	public void del(int matchingNum) {
		dao.deleteById(matchingNum);
	}

	// 사용자 마다 리스트
	public ArrayList<AutoMatchingDto> getUser(Member userNum) {
		ArrayList<AutoMatching> list = dao.findByUserNum(userNum);
		ArrayList<AutoMatchingDto> list2 = new ArrayList<AutoMatchingDto>();
		for (AutoMatching vo : list) {
			list2.add(new AutoMatchingDto(vo.getMatchingNum(), vo.getUserNum(), vo.getType(), vo.getStartDate(),
					vo.getExDate(), vo.getMonthMin(), vo.getMonthMax(), vo.getIsMatching()));
		}
		return list2;
	}

	// 전체리스트
	public ArrayList<AutoMatchingDto> getAll() {
		ArrayList<AutoMatching> list = (ArrayList<AutoMatching>) dao.findAll();
		ArrayList<AutoMatchingDto> list2 = new ArrayList<AutoMatchingDto>();
		for (AutoMatching vo : list) {
			list2.add(new AutoMatchingDto(vo.getMatchingNum(), vo.getUserNum(), vo.getType(), vo.getStartDate(),
					vo.getExDate(), vo.getMonthMin(), vo.getMonthMax(), vo.getIsMatching()));
		}
		return list2;
	}

	// isMatching 0에서 1로 바꾸기
	// -> 파티가 성사 되었다고 하는거니까 partygroup에 추가 하기
	public void changeIsMatching(AutoMatchingDto autoMatching, HostBoardDto hostBoard) {
		System.out.println("서비스까지들어옴");
		dao.isMatching(autoMatching.getMatchingNum()); // 0->1 로 바꾸고
		AutoMatching vo = dao.findById(autoMatching.getMatchingNum()).orElse(null);

		HostBoard hbvo = new HostBoard();
		hbvo.setBoardNum(hostBoard.getBoardNum());
		hbvo.setUserNum(hostBoard.getUserNum());
		hbvo.setType(hostBoard.getType());
		hbvo.setMonthPrice(hostBoard.getMonthPrice());
		hbvo.setOttAcct(hostBoard.getOttAcct());
		hbvo.setOttPwd(hostBoard.getOttPwd());
		hbvo.setMinPpl(hostBoard.getMinPpl());
		hbvo.setMaxPpl(hostBoard.getMaxPpl());
		hbvo.setSubStart(hostBoard.getSubStart());
		hbvo.setSubEnd(hostBoard.getSubEnd());
		hbvo.setMonth(hostBoard.getMonth());
		hbvo.setIng(hostBoard.getIng());

		
		PartyGroupDto partygroupdto = new PartyGroupDto();
		partygroupdto.setBoardNum(hbvo);
		partygroupdto.setUserNum(vo.getUserNum());
		partygroupdto.setMatchingNum(vo);
		System.out.println(partygroupdto);
		PGService.save(partygroupdto);

	}

	// 아직 매칭 안된(ismatching 이 0) 행들만 가져오기
	public ArrayList<AutoMatchingDto> getUnmatched() {
		ArrayList<AutoMatching> list = dao.getUnMatched();
		ArrayList<AutoMatchingDto> list2 = new ArrayList<AutoMatchingDto>();
		for (AutoMatching vo : list) {
			list2.add(new AutoMatchingDto(vo.getMatchingNum(), vo.getUserNum(), vo.getType(), vo.getStartDate(),
					vo.getExDate(), vo.getMonthMin(), vo.getMonthMax(), vo.getIsMatching()));
		}
		return list2;
	}

}
