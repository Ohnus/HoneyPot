package com.example.demo.hostboard;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.ott.Ott;

import jakarta.transaction.Transactional;

@Repository
public interface HostBoardDao extends JpaRepository<HostBoard, Integer> {

	// ott 종류로 찾기
	ArrayList<HostBoard> findByTypeOrderByBoardNumAsc(Ott type);

	// ing 0으로 바꾸는 Query 필요 -> 탈주범 생길 때 조절 할 것
	@Transactional
	@Modifying
	@Query(value = "update host_board set ing=0 where board_num=:board_num", nativeQuery = true)
	void updateIngTo0(@Param("board_num") int board_num);
	
	// ing 1으로 바꾸는 Query 필요 -> 스케쥴 돌릴 때 사용할 것임
	@Transactional
	@Modifying
	@Query(value = "update host_board set ing=1 where board_num=:board_num and ing <> 1", nativeQuery = true)
	void updateIngTo1(@Param("board_num") int board_num);
	
	//ing가 0 인것만 뽑아 
	@Transactional
	@Modifying
	@Query(value = "select * from host_board where ing=0", nativeQuery = true)
	ArrayList<HostBoard> getIng0();
	

}
