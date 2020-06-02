package com.fis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fis.model.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {}
