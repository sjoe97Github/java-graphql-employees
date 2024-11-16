package com.example.employees.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "employees")
@JsonPropertyOrder({ "id", "firstName", "lastName", "gender", "birthDate", "hireDate", "salaries", "titles" })
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_no", nullable = false)
    @JsonProperty("id")
    private Integer id;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "first_name", nullable = false, length = 14)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 16)
    private String lastName;

    @Lob
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @OneToMany(mappedBy = "empNo")
    @JsonProperty("salaries")
    private List<Salary> salaries;

    public List<Salary> getSalaries() {
        return Collections.unmodifiableList(salaries);
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }

    @OneToMany(mappedBy = "empNo")
    @JsonProperty("titles")
    private List<Title> titles;

    public List<Title> getTitles() {
        return Collections.unmodifiableList(titles);
    }

    public void setTitles(List<Title> title) {
        this.titles = title;
    }

    @OneToMany(mappedBy = "empNo")
    @JsonIgnore
    private List<DeptEmp> depEmps;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}