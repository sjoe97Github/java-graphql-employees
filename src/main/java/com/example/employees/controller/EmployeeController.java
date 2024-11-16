// src/main/java/com/example/employees/controller/EmployeeController.java
package com.example.employees.controller;

import com.example.employees.entities.Employee;
import com.example.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/byFirstName")
    public ResponseEntity<List<Employee>> getEmployeesByFirstName(@RequestParam String firstName) {
        List<Employee> employees = employeeService.findByFirstName(firstName);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/byLastName")
    public ResponseEntity<List<Employee>> getEmployeesByLastName(@RequestParam String lastName) {
        List<Employee> employees = employeeService.findByLastName(lastName);
        return ResponseEntity.ok(employees);
    }
}