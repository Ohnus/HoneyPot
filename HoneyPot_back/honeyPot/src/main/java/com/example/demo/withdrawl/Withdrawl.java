package com.example.demo.withdrawl;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
public class Withdrawl {

	@Id
	@SequenceGenerator(name="seq_withdrawl", sequenceName="seq_withdrawl", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_withdrawl")
	private int withdrawlNum;
	
	@ManyToOne
	@JoinColumn(name="userNum", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member userNum;
	
	@Column(columnDefinition = "date")
	private LocalDate withdrawlDate; // 출금 날짜
	
	private int amount; // 출금액
	
}
