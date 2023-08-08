package com.example.demo.pending;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.hostBoard.HostBoard;
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
public class Pending {
	
	@Id
	@SequenceGenerator(name="seq_pending", sequenceName="seq_pending", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_pending")
	private int pendingNum;
	
	@ManyToOne
	@JoinColumn(name="boardNum", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private HostBoard boardNum;
	
	@ManyToOne
	@JoinColumn(name="userNum", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User userNum;
	
	@Column(columnDefinition = "date")
	private LocalDate payDate; // 결제일..
	
	private int amount; // 월구독료 또는 보증금..
	
	private int msg; // 0: 구독료 / 1: 보증금 / 2: 돈나감
}
