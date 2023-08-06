package com.example.demo.ott;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
//@NoArgsConstructor
@AllArgsConstructor
@Component
public class Ott {
	private final OttProperties ottProperties;
	
	@Autowired
    public Ott(OttProperties ottProperties) {
        this.ottProperties = ottProperties;
    }
    
	@Id
	 private String type;
    private int price;
    private int maxPpl;
        
}
