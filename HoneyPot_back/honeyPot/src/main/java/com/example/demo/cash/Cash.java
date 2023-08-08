package com.example.demo.cash;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.users.User;

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
public class Cash {

	@Id
	@SequenceGenerator(name="seq_cash", sequenceName="seq_cash", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_cash")
	private int cashNum;
	
	@ManyToOne
	@JoinColumn(name="userNum", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User userNum;
	
	@Column(columnDefinition = "date")
	private LocalDate addDate; // 캐시 적립 날짜..
	
	private String msg; // 구독료입금, 보증금입금..
	
	private int amount; // 구독료 또는 보증금 액수..
	
}
