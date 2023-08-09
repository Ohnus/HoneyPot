package com.example.demo.chatCheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatCheckDao extends JpaRepository<ChatCheck, Integer> {

}
