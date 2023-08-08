package com.example.demo.ott;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OttDao extends JpaRepository<Ott, Integer> {

}
