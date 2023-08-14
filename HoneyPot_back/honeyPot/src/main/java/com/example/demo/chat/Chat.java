package com.example.demo.chat;


import java.time.LocalDateTime;

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
public class Chat {

	@Id
	@SequenceGenerator(name="chatNum", sequenceName="chatNum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="chatNum")
	@Column(name = "chatNum")
	private int chatNum; // 각 메세지 한줄에 대한 번호

	private int boardNum; // 메세지의 해당 채팅방 번호
	
	@ManyToOne
	@JoinColumn(name="isFromSender", nullable=true)  
	@OnDelete(action = OnDeleteAction.SET_NULL) 
	private Member isFromSender; // 메세지 작성자
	
	private String content; // 메세지 내용
	
	private LocalDateTime time;	
	
	public void preprosess() {
		time = LocalDateTime.now();
	}
	
	

	
}


