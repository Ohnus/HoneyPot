package com.example.demo.pending;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingDao extends JpaRepository<Pending, Integer> {

}
