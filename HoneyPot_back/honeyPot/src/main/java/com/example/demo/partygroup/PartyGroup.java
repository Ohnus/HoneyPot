package com.example.demo.partygroup;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.hostBoard.HostBoard;
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
public class PartyGroup {
	
	@Id
	@SequenceGenerator(name="seq_groupNum", sequenceName="seq_groupNum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_groupNum")
	@Column(name = "groupNum")
	private int groupNum;
	
	@ManyToOne
	@JoinColumn(name="boardNum", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private HostBoard boardNum;
	
	@ManyToOne
	@JoinColumn(name="userNum", nullable=true)  
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Member userNum;
	
	@ColumnDefault("0")
	@Column(nullable = false)
	private int startCheck;

}
