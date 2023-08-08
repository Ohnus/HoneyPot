package com.example.demo.ott;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class OttDatabase {
	
	private final OttDao ottRepository;
    private final OttProperties ottProperties;

    @Autowired
    public OttDatabase(OttDao  ottRepository, OttProperties ottProperties) {
        this.ottRepository = ottRepository;
        this.ottProperties = ottProperties;
    }

    @PostConstruct
    public void init() {
        List<Ott> otts = ottProperties.getVo();
        ottRepository.saveAll(otts);
    }

}
