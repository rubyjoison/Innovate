package com.fis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fis.model.Users;


@Repository
public interface UserDao extends JpaRepository<Users, Long>{
	
	Users findByUsername(String username);

}
