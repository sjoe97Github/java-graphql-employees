// src/main/java/com/example/employees/service/EmployeeService.java
package com.example.employees.service;

import com.example.employees.entities.Employee;
import com.example.employees.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findByFirstName(String firstName) {
        return (List<Employee>) employeeRepository.findByFirstName(firstName);
    }

    public List<Employee> findByLastName(String lastName) {
        return (List<Employee>) employeeRepository.findByLastName(lastName);
    }
}