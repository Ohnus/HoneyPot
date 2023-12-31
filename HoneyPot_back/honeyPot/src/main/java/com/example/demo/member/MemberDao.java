package com.example.demo.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao extends JpaRepository<Member, String> {
	Member findByEmail(String email);
	Member findByNickname(String nickname);
	Member findByNameAndPhone(String name, String phone);
	Member findByNameAndEmail(String name, String email);
	
}
