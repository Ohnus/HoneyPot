package com.example.demo.cash;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashDao extends JpaRepository<Cash, Integer> {

}