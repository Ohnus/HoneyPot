package com.example.demo.automatching;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;

@Repository
public interface AutoMatchingDao extends JpaRepository<AutoMatching, Integer> {

	//사용자 별로 찾기 
	ArrayList<AutoMatching> findByUserNum(Member userNum);
}
