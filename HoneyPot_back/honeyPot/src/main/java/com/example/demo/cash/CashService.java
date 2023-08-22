package com.example.demo.cash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashService {
	
	@Autowired
	private CashDao dao;
	
	// 캐시 적립
	public CashDto save(CashDto dto) {
		Cash entity = dao.save(new Cash(dto.getCashNum(), dto.getUserNum(), dto.getAddDate(), dto.getMsg(), dto.getAmount()));
		return new CashDto(entity.getCashNum(), entity.getUserNum(), entity.getAddDate(), entity.getMsg(), entity.getAmount());
	}
	
	//S
	
	
}
