package com.example.demo.chatheader;

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
public class ChatHeader {
	
	@Id
	@SequenceGenerator(name="channelNum", sequenceName="channelNum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="channelNum")
	@Column(name = "channelNum")
	private int channelNum; // 각 채팅방 번호
	
	private int boardNum; // 각 채팅방의 고유번호
	
	private String subject; // 채팅방 이름
	
	private Member host; // 채팅방 방장

}
