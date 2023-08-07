package com.example.demo.hostBoard;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.ott.Ott;

@Repository
public interface HostBoardDao extends JpaRepository<HostBoard, Integer> {

	//ott 종류로 찾기 
	 ArrayList<HostBoard> findByTypeOrderByBordNumAsc(Ott type);
	 
	 //시작 날짜 빠른 순으로 찾기
	 
	 //
}
