package com.example.demo.partygroup;

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
@RequestMapping("/partygroup")
public class PartyGroupController {
	
	@Autowired 
	private PartyGroupService PGService;
	
	//추가 
	@PostMapping("")
	private Map add(PartyGroupDto dto) {
		System.out.println(dto);
		PartyGroupDto dto2 = PGService.save(dto);
		Map map = new HashMap();
		map.put("dto", dto2);
		return map;
	}
	
	//삭제 
	@DeleteMapping("/{groupNum}")
	private Map delGroup(@PathVariable("groupNum") int groupNum) {
		Map map = new HashMap();
		boolean flag = true; 
		try {
			PGService.delete(groupNum);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map; 
	}
	
	

}
