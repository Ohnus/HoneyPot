package com.example.demo.ott;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("ott")
public class OttProperties {
	
	private List<Ott> vo;

    public List<Ott> getVo() {
        return vo;
    }

    public void setVo(List<Ott> vo) {
        this.vo = vo;
    }

}

