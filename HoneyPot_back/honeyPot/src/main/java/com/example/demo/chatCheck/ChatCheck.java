package com.example.demo.chatCheck;

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
public class ChatCheck {

	@Id
	@SequenceGenerator(name="checkNum", sequenceName="checkNum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="checkNum")
	@Column(name = "checkNum")
	private int checkNum; // 각 메세지별 수신자별 메세지 읽음 여부 확인
	
	private int messageNum; // 해당 메세지 번호
	
	private int boardNum; // 해당 메세지의 채팅방 번호
	
	private String toReciever; // 메세지를 확인하는 수신자
	
	private int read; // 읽음 여부 확인 0 : 안읽음, 1: 읽음
	
	
}
