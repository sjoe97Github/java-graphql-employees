package com.example.employees.repository;

import com.example.employees.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT d FROM Department d JOIN d.deptEmps de JOIN de.empNo e JOIN e.titles t JOIN e.salaries s WHERE d.deptName = :deptName AND t.id.title = :title AND s.salary = :salary")
    List<Department> findDepartmentsByEmployeeTitleAndSalary(@Param("deptName") String deptName, @Param("title") String title, @Param("salary") Integer salary);

    // return total number of employees in a department
    @Query("SELECT COUNT(e) FROM Department d JOIN d.deptEmps de JOIN de.empNo e WHERE d.deptName = :deptName")
    Integer countEmployeesByDepartmentName(@Param("deptName") String deptName);

    // return a map of total number of employees in each department by department name
    @Query("SELECT new map(d.deptName as name, COUNT(e) as count) FROM Department d JOIN d.deptEmps de JOIN de.empNo e GROUP BY d.deptName")
    List<Map<String, Integer>> countEmployeesByDepartment();

}