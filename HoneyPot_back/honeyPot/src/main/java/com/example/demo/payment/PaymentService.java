package com.example.demo.payment;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentDao dao;
	
	// 결제내역 등록
	public PaymentDto save(PaymentDto dto) {
		Payment entity = dao.save(new Payment(dto.getOrderNum(), dto.getUserNum(), dto.getBoardNum(), dto.getPayInstallment(), dto.getFinalInstallment(), dto.getRecurringDay(), dto.getPayDate(), dto.getNextDate(), dto.getTotalPayment(), dto.getDeposit(), dto.getMonthPrice(), dto.getCommission(), dto.getHistory(), dto.getOttType()));
		return new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getOttType());
	}
	
	// 결제내역 단건 불러오기(단일 내역 부분수정)
	public PaymentDto getPayment(String orderNum) {
		Payment entity = dao.findById(orderNum).orElse(null);
		if(entity == null) {
			return null;
		} else {
			return new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getOttType());
		}
		
	}
	
	// 결제내역 리스트 불러오기
	public ArrayList<PaymentDto> getAll() {
		ArrayList<Payment> list = (ArrayList<Payment>) dao.findAll();
		ArrayList<PaymentDto> dtoList = new ArrayList<PaymentDto>();
		for(Payment entity : list) {
			dtoList.add(new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getOttType()));
		}
		return dtoList;
	}
	
	// RecurringDay 리스트
	public ArrayList<PaymentDto> getByRcrDay(int recurringDay) {
		ArrayList<Payment> list = (ArrayList<Payment>) dao.findByRcrDay(recurringDay);
		ArrayList<PaymentDto> dtoList = new ArrayList<PaymentDto>();
		for(Payment entity : list) {
			dtoList.add(new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getOttType()));
		}
		return dtoList;
	}
	
	// (payDate = nextDate) && 결제회차 < 마지막회차 리스트
	public ArrayList<PaymentDto> getByNextDate(LocalDate nextDate) {
		ArrayList<Payment> list = (ArrayList<Payment>) dao.findByNextDate(nextDate);
		ArrayList<PaymentDto> dtoList = new ArrayList<PaymentDto>();
		for(Payment entity : list) {
			dtoList.add(new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getOttType()));
		}
		return dtoList;
	}
	
}