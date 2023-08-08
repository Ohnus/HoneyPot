package com.example.demo.ott;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OttController {
	
	    private final OttProperties ottProperties;

	    @Autowired
	    public OttController (OttProperties ottProperties) {
	        this.ottProperties = ottProperties;
	    }

	    @GetMapping("/services")
	    public List<Ott> getServices() {
	        return ottProperties.getVo();
	    }
	


}
