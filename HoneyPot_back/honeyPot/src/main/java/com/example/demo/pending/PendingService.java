package com.example.demo.pending;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.hostboard.HostBoard;
import com.example.demo.member.Member;

@Service
public class PendingService {

	@Autowired
	private PendingDao dao;
	
	// Payment 테이블 등록과 동시에 Pending 테이블도 같이 등록
	public PendingDto save(PendingDto dto) {
		Pending entity = dao.save(new Pending(dto.getPendingNum(), dto.getBoardNum(), dto.getUserNum(), dto.getPayDate(), dto.getReturnDate(), dto.getAmount(), dto.getMsg()));
		return new PendingDto(entity.getPendingNum(), entity.getBoardNum(), entity.getUserNum(), entity.getPayDate(), entity.getReturnDate(), entity.getAmount(), entity.getMsg());
	}
	
	// 단건 조회(부분 수정용)
	public PendingDto getPending(int pendingNum) {
		Pending entity = dao.findById(pendingNum).orElse(null);
		if(entity == null) {
			return null;
		} else {
			return new PendingDto(entity.getPendingNum(), entity.getBoardNum(), entity.getUserNum(), entity.getPayDate(), entity.getReturnDate(), entity.getAmount(), entity.getMsg());
		}
	}
	
	// 단건 조회(위약금)
	public PendingDto getDeposit(HostBoard boardNum, Member userNum) {
		Pending entity = dao.findByBoardNumAndUserNumAnsMsg(boardNum, userNum);
		if(entity == null) {
			return null;
		} else {
			return new PendingDto(entity.getPendingNum(), entity.getBoardNum(), entity.getUserNum(), entity.getPayDate(), entity.getReturnDate(), entity.getAmount(), entity.getMsg());
		}
	}
	
	// 구독료 적립 리스트
	public ArrayList<PendingDto> getEarningList(LocalDate earningDate) {
		ArrayList<Pending> list = (ArrayList<Pending>) dao.findByEarningDate(earningDate);
		ArrayList<PendingDto> dtoList = new ArrayList<PendingDto>();
		for(Pending entity : list) {
			dtoList.add(new PendingDto(entity.getPendingNum(), entity.getBoardNum(), entity.getUserNum(), entity.getPayDate(), entity.getReturnDate(), entity.getAmount(), entity.getMsg()));
		}
		return dtoList;
	}
	
	// 보증금 반환 리스트
	public ArrayList<PendingDto> getReturnList(LocalDate returnDate) {
		ArrayList<Pending> list = (ArrayList<Pending>) dao.findByEarningDate(returnDate);
		ArrayList<PendingDto> dtoList = new ArrayList<PendingDto>();
		for(Pending entity : list) {
			dtoList.add(new PendingDto(entity.getPendingNum(), entity.getBoardNum(), entity.getUserNum(), entity.getPayDate(), entity.getReturnDate(), entity.getAmount(), entity.getMsg()));
		}
		return dtoList;
	}
}
