package com.example.demo.pending;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PendingService {

	@Autowired
	private PendingDao dao;
	
	// Payment 테이블 등록과 동시에 Pending 테이블도 같이 등록
	public PendingDto save(PendingDto dto) {
		Pending entity = dao.save(new Pending(dto.getPendingNum(), dto.getBoardNum(), dto.getUserNum(), dto.getPayDate(), dto.getAmount(), dto.getMsg()));
		return new PendingDto(entity.getPendingNum(), entity.getBoardNum(), entity.getUserNum(), entity.getPayDate(), entity.getAmount(), entity.getMsg());
	}
	
	// 단건 조회(부분 수정용)
	public PendingDto getPending(int pendingNum) {
		Pending entity = dao.findById(pendingNum).orElse(null);
		if(entity == null) {
			return null;
		} else {
			return new PendingDto(entity.getPendingNum(), entity.getBoardNum(), entity.getUserNum(), entity.getPayDate(), entity.getAmount(), entity.getMsg());
		}
	}
}
