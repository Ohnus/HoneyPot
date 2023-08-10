package com.example.demo.automatching;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/AutoMatching")
public class AutoMatchingController {
	
	@Autowired
	private AutoMatchingService AMService;
	
	//추가 
	@PostMapping("")
	public Map add(AutoMatchingDto dto) {
		Map map = new HashMap(); 
		AutoMatchingDto dto2 = AMService.save(dto);
		map.put("dto", dto2);
		return map;
	}
	
	//삭제
	@DeleteMapping("/{matchingNum}")
	public void del(@PathVariable("matchingNum") int matchigNum) {
	AMService.del(matchigNum);	
	}
		

}
