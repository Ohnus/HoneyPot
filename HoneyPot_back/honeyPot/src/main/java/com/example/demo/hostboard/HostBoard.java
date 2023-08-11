package com.example.demo.hostboard;

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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
public class HostBoard {

	@Id
	@SequenceGenerator(name="seq_boardNum", sequenceName="seq_boardNum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_boardNum")
	@Column(name = "boardNum")
	private int boardNum; //게시판 글 번호 
	
	@ManyToOne
	@JoinColumn(name="userNum", nullable=true)  
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Member userNum; //작성자 
	
	@ManyToOne
	@JoinColumn(name="type", nullable=true)  
	@OnDelete(action = OnDeleteAction.SET_NULL) 
	private Ott type; //ott 이름
	
	private int monthPrice; //인당 월결제비용 : ott.price / maxpeople
	
	private String ottAcct; //ott 아이디
	private String ottPwd; //ott 비밀번호
	
	private int minPpl; //참여 최소인원 (작성자포함)
	private int maxPpl; //참여 최대인원 (작성자 포함)
	
	private LocalDate subStart; //구독 시작일 
	private LocalDate subEnd; //구독 끝날짜 
	private int month; //몇개월인지를 클릭해서 subEnd에 넣기 
	
	 @ColumnDefault("0")
	 @Column(nullable = false)
	private int ing =0; // 0: 리스트에서 보여줌 1: 리스트에서 안보여줌 
	  	
	 @PrePersist
	    public void calculate() {
		 //monthPrice 계산 
		 monthPrice = type.getPrice() / maxPpl;
		 
		 //subEnd 계산
		 subEnd = subStart.plusMonths(month);
		
	 }
			
	 
}
