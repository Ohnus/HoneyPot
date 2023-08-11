package com.example.demo.ott;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OttConverter implements Converter<String, Ott> {

    private final OttRepository ottRepository;

    public OttConverter(OttRepository ottRepository) {
        this.ottRepository = ottRepository;
    }

    @Override
    public Ott convert(String source) {
        return ottRepository.findByType(source); // OttRepository에서 해당 "type" 값을 찾는 로직
    }
}
