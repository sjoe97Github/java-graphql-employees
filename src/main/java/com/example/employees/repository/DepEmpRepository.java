package com.example.employees.repository;

import com.example.employees.entities.DeptEmp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepEmpRepository extends JpaRepository<DeptEmp, Long> {
}