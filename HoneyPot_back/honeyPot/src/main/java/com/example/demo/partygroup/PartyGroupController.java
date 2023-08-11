package com.example.demo.partygroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.hostboard.HostBoard;
import com.example.demo.hostboard.HostBoardService;
import com.example.demo.member.Member;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/partygroup")
public class PartyGroupController {

	@Autowired
	private PartyGroupService PGService;
	
	@Autowired
	private HostBoardService HBService;

	// 추가 : 파티장이 글 등록하자마자 여기로 감
	@PostMapping("")
	public Map add(PartyGroupDto dto) {
		System.out.println(dto);
		PartyGroupDto dto2 = PGService.save(dto);
		Map map = new HashMap();
		map.put("dto", dto2);
		return map;
	}

	// 삭제 : 오늘 날짜가 구독 시작 날짜 3일 전이면 취소 가능
	@DeleteMapping("/{groupNum}")
	public Map delGroup(@PathVariable("groupNum") int groupNum) {
		Map map = new HashMap();
		PartyGroupDto dto = PGService.finByGroupNum(groupNum);
		LocalDate today = LocalDate.now();
		if (dto.getBoardNum().getSubStart().minusDays(3).isBefore(today)) {
			PGService.delete(groupNum);
			map.put("msg", "삭제가 완료 되었습니다.");
		} else {
			map.put("msg", "곧 구독이 시작되어 취소가 불가능합니다");
		}
		return map;
	}

	// 한명 당 뭐뭐에 파티 있나 보여주기 -> 마이페이지에서 띄울 것임 (Startcheck로 확인해서 분류별로)
	@GetMapping("/{userNum}")
	public Map getUserList(@PathVariable("userNum") Member userNum) {
		Map map = new HashMap();
		ArrayList<PartyGroupDto> list = PGService.getUserList(userNum);
		map.put("list", list);
		return map;
	}

	// 파티원들 등록
	@PostMapping("/{boardNum}/{userNum}")
	public Map addParty(@PathVariable("boardNum") HostBoard boardNum, @PathVariable("userNum") Member userNum) {
		Map map = new HashMap<>();
		boolean flag = true;
		int count = PGService.findByBoardNum(boardNum); // 1. 몇개의 글이 있나 확인한다
		if (count >= boardNum.getMaxPpl()) { // 2. 글이 Maxppl 보다 크거나 같으면
			flag = false;
			map.put("flag", flag);
			map.put("message", "정원이 찬 파티로 등록 할 수 없습니다."); // 등록을 막아
		} else { // 아니라면 등록 해줌
			PartyGroupDto dto = new PartyGroupDto();
			dto.setBoardNum(boardNum);
			dto.setUserNum(userNum);
//			dto.setStartCheck(0);
			PartyGroupDto savedDto = PGService.save(dto);
			map.put("dto", savedDto);
			map.put("flag", flag);
		}
		return map;
	}

	// 중간 탈퇴를 하고 싶어요
	@GetMapping("/{boardNum}")
	public Map middleOut(@PathVariable("boardNum") int boardNum) {
		Map map = new HashMap();
		Map Result = PGService.editStartTo4(boardNum); //결과를 받아서 넣고 
		boolean flag = (boolean) Result.get("flag");
		if (flag) { //flag가 true 여서 중간 탈퇴가 진행 되었다면 
			HBService.changIngToZero(boardNum); //ing 를 0 으로 바꿔서 리스트에 보이게 해줘 
		}
		map.put("flag", flag);
		return map;
	} 

}
