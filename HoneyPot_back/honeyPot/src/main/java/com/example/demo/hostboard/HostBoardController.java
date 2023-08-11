package com.example.demo.hostboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ott.Ott;
import com.example.demo.partygroup.PartyGroupController;
import com.example.demo.partygroup.PartyGroupService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hostboard")
public class HostBoardController {

	@Autowired
	private HostBoardService HBService;

	@Autowired
	private PartyGroupService PGService;

	@Autowired
	private PartyGroupController PGC;

	// 글 추가
	@PostMapping("")
	public Map add(HostBoardDto dto) {
		Map map = new HashMap();
		// 글을 등록
		HostBoardDto dto2 = null;
		boolean flag = true;
		try {
			int boardnum = HBService.save(dto);
			dto2 = HBService.getBoard(boardnum);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		map.put("dto2", dto2);
		System.out.println("@@@추가!!! " + dto2);
		return map;
	}

	// 글 삭제 : 참가자 있나 확인하고 있으면 취소 못함! 
	@DeleteMapping("/{boardNum}")
	public Map del(@PathVariable("boardNum") int boardNum) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			HBService.delete(boardNum);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}

	// 전체 글 목록 (필요할까?)
	@GetMapping("")
	public Map getAll() {
		ArrayList<HostBoardDto> list = HBService.getAll();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	// 사이트별로 리스트 
	@GetMapping("/{ott}")
	public Map getBySite(@PathVariable("ott") Ott ott) {
		ArrayList<HostBoardDto> list = HBService.getottAll(ott);
		Map map = new HashMap();
		map.put("list", list);
		return map;

	}
}
