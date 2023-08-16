package com.example.demo.automatching;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;

import jakarta.transaction.Transactional;

@Repository
public interface AutoMatchingDao extends JpaRepository<AutoMatching, Integer> {

	//사용자 별로 찾기 
	ArrayList<AutoMatching> findByUserNum(Member userNum);
	
	//isMatching Column 매칭 되었다면 1로 바꾸기 
	@Transactional
	@Modifying
	@Query (value = "update auto_matching set is_matching=1 where matching_num=:matching_num",  nativeQuery = true)
	void isMatching(@Param ("matching_num") int matching_num);

	@Transactional
	@Modifying
	@Query (value = "select * from auto_matching where is_matching=0",  nativeQuery = true)
	ArrayList<AutoMatching> getUnMatched();

}
