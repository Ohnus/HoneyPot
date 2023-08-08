package com.example.demo.chat;

import com.example.demo.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@SequenceGenerator(name="messageNum", sequenceName="messageNum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="messageNum")
	@Column(name = "messageNum")
	private int messageNum; // 각 메세지 한줄에 대한 번호
	
	private int boardNum; // 메세지의 해당 채팅방 번호
	
	private Member isFromSender; // 메세지 작성자
	
	private String content; // 메세지 내용
	
}


