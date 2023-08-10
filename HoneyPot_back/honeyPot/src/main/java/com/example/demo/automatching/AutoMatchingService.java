package com.example.demo.automatching;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;

@Service
public class AutoMatchingService {

	@Autowired
	private AutoMatchingDao dao;

	// 추가와 수정
	public AutoMatchingDto save(AutoMatchingDto dto) {
		AutoMatching vo = dao.save(new AutoMatching(dto.getMatchingNum(), dto.getUserNum(), dto.getType(),
				dto.getSubStart(), dto.getExDate(), dto.getMonthMin(), dto.getMonthMax()));
		return new AutoMatchingDto(vo.getMatchingNum(), vo.getUserNum(), vo.getType(), vo.getSubStart(), vo.getExDate(),
				vo.getMonthMin(), vo.getMonthMax());
	}

	// 삭제
	public void del(int matchingNum) {
		dao.deleteById(matchingNum);
	}

	// 사용자 마다 리스트
	public ArrayList<AutoMatchingDto> getUser(Member userNum) {
		ArrayList<AutoMatching> list = dao.findByUserNum(userNum);
		ArrayList<AutoMatchingDto> list2 = new ArrayList<AutoMatchingDto>();
		for (AutoMatching vo : list) {
			list2.add(new AutoMatchingDto(vo.getMatchingNum(), vo.getUserNum(), vo.getType(), vo.getSubStart(),
					vo.getExDate(), vo.getMonthMin(), vo.getMonthMax()));
		}
		return list2;
	}

}
