package com.example.demo.payment;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.hostboard.HostBoard;
import com.example.demo.member.Member;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentDao dao;
	
	// 결제내역 등록
	public PaymentDto save(PaymentDto dto) {
		Payment entity = dao.save(new Payment(dto.getOrderNum(), dto.getUserNum(), dto.getBoardNum(), dto.getPayInstallment(), dto.getFinalInstallment(), dto.getRecurringDay(), dto.getPayDate(), dto.getNextDate(), dto.getAgainDate(), dto.getTotalPayment(), dto.getDeposit(), dto.getMonthPrice(), dto.getCommission(), dto.getHistory(), dto.getPaymentStatus(), dto.getOttType()));
		return new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getAgainDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getPaymentStatus(), entity.getOttType());
	}
	
	// 결제내역 단건 불러오기(단일 내역 부분수정)
	public PaymentDto getPayment(String orderNum) {
		Payment entity = dao.findById(orderNum).orElse(null);
		if(entity == null) {
			return null;
		} else {
			return new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getAgainDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getPaymentStatus(), entity.getOttType());
		}
		
	}
	
	// 결제내역 리스트 불러오기
	public ArrayList<PaymentDto> getAll() {
		ArrayList<Payment> list = (ArrayList<Payment>) dao.findAll();
		ArrayList<PaymentDto> dtoList = new ArrayList<PaymentDto>();
		for(Payment entity : list) {
			dtoList.add(new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getAgainDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getPaymentStatus(), entity.getOttType()));
		}
		return dtoList;
	}
	
	// 탈퇴예정자의 보드넘, 유저넘 이용해서 최신순으로 리스트 조회 후, 최신글 리턴
	public PaymentDto getByBoardNumAndUserNum(HostBoard boardNum, Member userNum) {
		ArrayList<Payment> list = (ArrayList<Payment>) dao.findByBoardNumAndUserNum(boardNum, userNum);
		ArrayList<PaymentDto> dtoList = new ArrayList<PaymentDto>();
		for(Payment entity : list) {
			dtoList.add(new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getAgainDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getPaymentStatus(), entity.getOttType()));
		}
		return dtoList.get(0);
	}
	
	// RecurringDay 리스트
	public ArrayList<PaymentDto> getByRcrDay(int recurringDay) {
		ArrayList<Payment> list = (ArrayList<Payment>) dao.findByRcrDay(recurringDay);
		ArrayList<PaymentDto> dtoList = new ArrayList<PaymentDto>();
		for(Payment entity : list) {
			dtoList.add(new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getAgainDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getPaymentStatus(), entity.getOttType()));
		}
		return dtoList;
	}
	
	// (payDate = nextDate) && 결제회차 < 마지막회차 리스트
	public ArrayList<PaymentDto> getByNextDate(LocalDate nextDate) {
		ArrayList<Payment> list = (ArrayList<Payment>) dao.findByNextDate(nextDate);
		ArrayList<PaymentDto> dtoList = new ArrayList<PaymentDto>();
		for(Payment entity : list) {
			dtoList.add(new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getAgainDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getPaymentStatus(), entity.getOttType()));
		}
		return dtoList;
	}
	
	// againDate && paymentStatus -1 리스트
	public ArrayList<PaymentDto> getByAgainList(LocalDate againDate) {
		ArrayList<Payment> list = (ArrayList<Payment>) dao.findByAgainDate(againDate);
		ArrayList<PaymentDto> dtoList = new ArrayList<PaymentDto>();
		for(Payment entity : list) {
			dtoList.add(new PaymentDto(entity.getOrderNum(), entity.getUserNum(), entity.getBoardNum(), entity.getPayInstallment(), entity.getFinalInstallment(), entity.getRecurringDay(), entity.getPayDate(), entity.getNextDate(), entity.getAgainDate(), entity.getTotalPayment(), entity.getDeposit(), entity.getMonthPrice(), entity.getCommission(), entity.getHistory(), entity.getPaymentStatus(), entity.getOttType()));
		}
		return dtoList;
	}
	
}