package com.example.employees.repository;

import com.example.employees.entities.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Salary, Long> {
}
