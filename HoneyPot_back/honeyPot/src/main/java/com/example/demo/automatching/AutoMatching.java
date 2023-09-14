package com.example.demo.automatching;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.member.Member;
import com.example.demo.ott.Ott;

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
public class AutoMatching {

	@Id
	@SequenceGenerator(name="seq_matchingNum", sequenceName="seq_matchingNum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_matchingNum")
	@Column(name = "matchingNum")
	private int matchingNum;
	
	@ManyToOne
	@JoinColumn(name="userNum", nullable=true)  
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Member userNum; 
	
	@ManyToOne
	@JoinColumn(name="type", nullable=true)  
	@OnDelete(action = OnDeleteAction.SET_NULL) 
	private Ott type; 
	
	private LocalDate startDate; //언제시작인 것? 
	private LocalDate exDate; // 마감일자는? 
	private int monthMin; //최소 몇개월? 
	private int monthMax; // 최대 몇개월 
	
	 @ColumnDefault("0")
	 @Column(nullable = false)
	private int isMatching;
	
	
}
