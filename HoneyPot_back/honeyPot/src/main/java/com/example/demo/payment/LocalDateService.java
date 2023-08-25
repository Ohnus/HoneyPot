package com.example.demo.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class LocalDateService {
	
	public LocalDate getPayDate() {
		LocalDateTime today = LocalDateTime.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		int recurringDay = today.getDayOfMonth();
		String date = String.format("%02d/%02d/%02d", year % 100, month, recurringDay);
		DateTimeFormatter changePattern = DateTimeFormatter.ofPattern("yy/MM/dd");
		LocalDate payDate = LocalDate.parse(date, changePattern);
		
		return payDate;
	}
	
	public LocalDate getNextDate() {
		LocalDateTime today = LocalDateTime.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		int nextMonth = today.getMonthValue() + 1;
		int recurringDay = today.getDayOfMonth();
		String date = String.format("%02d/%02d/%02d", year % 100, month, recurringDay);
		String next = String.format("%02d/%02d/%02d", year % 100, nextMonth, recurringDay);
		DateTimeFormatter changePattern = DateTimeFormatter.ofPattern("yy/MM/dd");
		LocalDate nextDate = LocalDate.parse(next, changePattern);
		
		return nextDate;
	}
	
	public LocalDate getAgainDate() {
		LocalDate payDate = getPayDate();
		LocalDate againDate = payDate.plusWeeks(1);
		
		return againDate;
	}
	
	public LocalDate getEarningDate(LocalDate payDate) {
		int year = payDate.getYear();
		int month = payDate.getMonthValue() + 1;
		int returnDay = payDate.getDayOfMonth() - 1;
		String date = String.format("%02d/%02d/%02d", year % 100, month, returnDay);
		DateTimeFormatter changePattern = DateTimeFormatter.ofPattern("yy/MM/dd");
		LocalDate returnDate = LocalDate.parse(date, changePattern);
		
		return returnDate;
	}
	
	public int getRecurringDay() {
		LocalDateTime today = LocalDateTime.now();
		int recurringDay = today.getDayOfMonth();
		
		return recurringDay;
	}
	
	public String getMerchantUid() {
		LocalDateTime today = LocalDateTime.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		int recurringDay = today.getDayOfMonth();
		int hh = today.getHour();
		int mm = today.getMinute();
		String merchantUid = "HNP" + String.valueOf(year).substring(2) + String.format("%02d", month)
		+ String.format("%02d", recurringDay) + String.format("%02d", hh) + String.format("%02d", mm);
		
		return merchantUid;
	}
}
