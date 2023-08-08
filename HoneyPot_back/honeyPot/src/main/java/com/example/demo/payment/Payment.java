package com.example.demo.payment;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.hostBoard.HostBoard;
import com.example.demo.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	
	@Id
	private String orderNum; // 주문번호.. HP2308080334
	
	@ManyToOne
	@JoinColumn(name="userNum", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User userNum; // 회원번호.. NOT NULL
	
	@ManyToOne
	@JoinColumn(name="boardNum", nullable=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private HostBoard boardNum; // 게시글번호.. NULL(카드등록 100원 결제)
	
	@ColumnDefault("0")
	private int payInstallment; // 결제회차.. 
	@ColumnDefault("0")
	private long finalInstallment; // 마지막회차.. 게시글의 month 긁어오기
	@Column(columnDefinition = "date")
	@ColumnDefault("0")
	private LocalDate recurringDay; // 정기결제일.. 게시글의 substart의 일자
	@Column(columnDefinition = "date")
	private LocalDate payDate; // 실제 결제일..
	
	@ColumnDefault("0")
	private int totalPayment; // 총 결제금액..
	@ColumnDefault("0")
	private int deposit; // 보증금 부분..
	@ColumnDefault("0")
	private int monthPrice; // 월구독료..
	@ColumnDefault("0")
	private int commission; // 수수료.. 20%, 10%
	@ColumnDefault("None")
	private String history; // 결제내역..
	@ColumnDefault("None")
	private String ottType; // ott 타입.. 게시글에서 긁어오기
}
