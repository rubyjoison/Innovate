package com.fis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fis.model.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {}
