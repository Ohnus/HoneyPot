package com.example.demo.hostBoard;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.ott.Ott;

@Service
public class HostBoardService {

	@Autowired
	private HostBoardDao dao;

	// 등록 및 수정 
	public int save(HostBoardDto dto) {
		HostBoard vo = dao.save(new HostBoard(dto.getBoardNum(), dto.getUserNum(), dto.getType(), dto.getMonthPrice(),
				dto.getOttAcct(), dto.getOttPwd(), dto.getMinPpl(), dto.getMaxPpl(), dto.getSubStart(),
				dto.getSubEnd(), dto.getMonth(), dto.getIng()));
		return vo.getBoardNum();
	}

	// 삭제
	public void delete(int boardNum) {
		dao.deleteById(boardNum);
	}
	
	//글 전체 목록 
	public ArrayList<HostBoardDto> getAll() {
		ArrayList<HostBoard> list = (ArrayList<HostBoard>) dao.findAll(Sort.by(Sort.Direction.ASC, "boardNum"));
		ArrayList<HostBoardDto> list2 = new ArrayList<HostBoardDto>();
		for(HostBoard vo : list) {
			list2.add(new HostBoardDto(vo.getBoardNum(), vo.getUserNum(), vo.getType(), vo.getMonthPrice(),
					vo.getOttAcct(), vo.getOttPwd(), vo.getMinPpl(), vo.getMaxPpl(), vo.getSubStart(),
					vo.getSubEnd(), vo.getMonth(), vo.getIng()));
		}
		 return list2;
	}

	// 글 상세 목록
	public HostBoardDto getBoard(int boardNum) {
		HostBoard vo = dao.findById(boardNum).orElse(null);
		if(vo ==null) {
			return null;
		}
		return new HostBoardDto(vo.getBoardNum(), vo.getUserNum(), vo.getType(), vo.getMonthPrice(),
				vo.getOttAcct(), vo.getOttPwd(), vo.getMinPpl(), vo.getMaxPpl(), vo.getSubStart(),
				vo.getSubEnd(), vo.getMonth(), vo.getIng());
	}

	// ott별로 글 목록 뽑기 
	public ArrayList<HostBoardDto> getottAll(Ott type){
		ArrayList<HostBoard> list = (ArrayList<HostBoard>) dao.findByTypeOrderByBordNumAsc(type);
		ArrayList<HostBoardDto> list2 = new ArrayList<HostBoardDto>();
		for(HostBoard vo : list) {
			list2.add(new HostBoardDto(vo.getBoardNum(), vo.getUserNum(), vo.getType(), vo.getMonthPrice(),
					vo.getOttAcct(), vo.getOttPwd(), vo.getMinPpl(), vo.getMaxPpl(), vo.getSubStart(),
					vo.getSubEnd(), vo.getMonth(), vo.getIng()));
		}
		 return list2;
	}
}