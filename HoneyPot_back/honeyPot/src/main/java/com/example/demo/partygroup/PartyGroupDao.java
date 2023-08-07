package com.example.demo.partygroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyGroupDao extends JpaRepository<PartyGroup, Integer> {

}
