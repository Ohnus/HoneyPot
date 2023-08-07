package com.example.demo.hostBoard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/hostboard")
public class HostBoardController {
	
	@Autowired
	private HostBoardService service;
	
	@PostMapping("")
	public Map add (HostBoardDto dto) {
		Map map = new HashMap();
	
		return map;
	}

}
