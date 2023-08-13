package com.example.demo.hostboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ott.Ott;
import com.example.demo.partygroup.PartyGroupController;
import com.example.demo.partygroup.PartyGroupDto;
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

	//파티장 글 등록 
	@PostMapping("")
	public Map add(HostBoardDto dto) {
		Map map = new HashMap();
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
		
		HostBoard hostBoard = new HostBoard();
		hostBoard.setBoardNum(dto2.getBoardNum());
		
		PartyGroupDto partyGroupDto = new PartyGroupDto();

		partyGroupDto.setBoardNum(hostBoard); 
		partyGroupDto.setUserNum(dto2.getUserNum());
		partyGroupDto.setStartCheck(0); // 또는 1

		PartyGroupDto savPG = PGService.save(partyGroupDto);
		System.out.println("PartyGroupDto savPG : " + savPG);
		return map;
	}

	// 글 삭제 : 시작했으면 취소 못함! -> 시작 안했으면 취소 언제든지 가능
	@DeleteMapping("/{boardNum}")
	public Map del(@PathVariable("boardNum") int boardNum) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<PartyGroupDto> list = PGService.findByStartCheck(boardNum, 0); // startcheck가 0인 것들 골라와
		if (list != null) { // 0 이라면
			HBService.delete(boardNum); // 취소 가능
		} else { // 아니라면 취소 불가능
			flag = false;
			map.put("msg", "삭제가 불가능합니다. 고객센터에 문의 해주세요");
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

	// 상세 검색
	@GetMapping("/{boardNum}")
	public Map getByNum(@PathVariable("boardNum") int boardNum) {
		HostBoardDto dto = HBService.getBoard(boardNum);
		Map map = new HashMap();
		map.put("dto", dto);
		return map;
	}

	// 사이트별로 리스트
	@GetMapping("site/{ott}")
	public Map getBySite(@PathVariable("ott") Ott ott) {
		ArrayList<HostBoardDto> list = HBService.getottAll(ott);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
}
