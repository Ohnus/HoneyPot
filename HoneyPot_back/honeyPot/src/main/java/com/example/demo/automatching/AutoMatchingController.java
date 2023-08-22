package com.example.demo.automatching;

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

import com.example.demo.member.Member;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/automatching")
public class AutoMatchingController {
	
	@Autowired
	private AutoMatchingService AMService;
	
	//글 등록 해서 추가함 
	@PostMapping("")
	public Map add(AutoMatchingDto dto) {
		Map map = new HashMap(); 
		AutoMatchingDto dto2 = AMService.save(dto);
		map.put("dto", dto2);
		return map;
	}
	
	//자진 삭제 -> 파티 그냥 내가 찾을게~ 
	@DeleteMapping("/{matchingNum}")
	public void del(@PathVariable("matchingNum") int matchigNum) {
	AMService.del(matchigNum);	
	}
	
	//사용자별 매칭 기록 보기 
	@GetMapping("/{userNum}")
	public Map getUser(@PathVariable("userNum") Member userNum) {
		Map map = new HashMap();
		ArrayList<AutoMatchingDto> list = AMService.getUser(userNum);
		map.put("list", list);
		return map;
	}
	
	
		

}
