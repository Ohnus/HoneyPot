package com.example.demo.chatheader;

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
public class ChatHeader {
	
	@Id
	@SequenceGenerator(name="seq_chatroomNum", sequenceName="seq_chatroomNum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_chatroomNum")
	@Column(name = "chatroomNum")
	private int chatroomNum; // 각 채팅방 번호
	
	private int boardNum; // 각 채팅방의 고유번호
	
	private String subject; // 채팅방 이름
	
	@ManyToOne
	@JoinColumn(name="host", nullable=true)  
	@OnDelete(action = OnDeleteAction.SET_NULL) 
	private Member host; // 채팅방 방장

}
