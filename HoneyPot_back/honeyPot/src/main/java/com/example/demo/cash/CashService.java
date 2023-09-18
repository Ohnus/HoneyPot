package com.example.demo.cash;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;

@Service
public class CashService {
	
	@Autowired
	private CashDao dao;
	
	// 캐시 적립
	public CashDto save(CashDto dto) {
		Cash entity = dao.save(new Cash(dto.getCashNum(), dto.getUserNum(), dto.getAddDate(), dto.getMsg(), dto.getAmount()));
		return new CashDto(entity.getCashNum(), entity.getUserNum(), entity.getAddDate(), entity.getMsg(), entity.getAmount());
	}
	
	// 캐시 내역
	public ArrayList<CashDto> list() {
		ArrayList<Cash> list = (ArrayList<Cash>) dao.findAll();
		ArrayList<CashDto> dtoList = new ArrayList<CashDto>();
		for(Cash entity : list) {
			dtoList.add(new CashDto(entity.getCashNum(), entity.getUserNum(), entity.getAddDate(), entity.getMsg(), entity.getAmount()));
		}
		return dtoList;
	}
	
	// 유저 캐시 내역
	public ArrayList<CashDto> listByUser(String userNum) {
		ArrayList<Cash> list = (ArrayList<Cash>) dao.findAllByUser(userNum);
		ArrayList<CashDto> dtoList = new ArrayList<CashDto>();
		for(Cash entity : list) {
			dtoList.add(new CashDto(entity.getCashNum(), entity.getUserNum(), entity.getAddDate(), entity.getMsg(), entity.getAmount()));
		}
		return dtoList;
	}
	
	// 캐시 총합
	public int total(String userNum) {
		System.out.println("서비스 캐시총합: " + dao.findTotalCashByUser(userNum));
		return dao.findTotalCashByUser(userNum);
	}
	
}
