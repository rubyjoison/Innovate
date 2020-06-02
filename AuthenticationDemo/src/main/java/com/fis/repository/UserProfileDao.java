package com.fis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fis.model.UserProfile;

@Repository
public interface UserProfileDao extends JpaRepository<UserProfile, Long> {}
