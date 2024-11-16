package com.example.employees.repository;

import com.example.employees.entities.Employee;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    // find employee record by first name
    @Nullable
    Iterable<Employee> findByFirstName(String firstName);

    // find employee record by last name
    @Nullable
    Iterable<Employee> findByLastName(String lastName);

    @Override
    Iterable<Employee> findAll();

    @Override
    Optional<Employee> findById(Integer integer);

    @Nullable
    List<Employee> findByGender(String gender);

    @Query("SELECT e FROM Employee e JOIN e.titles t WHERE t.id.title = :title")
    List<Employee> findEmployeesByTitle(@Param("title") String title);

    @Query("SELECT e FROM Employee e JOIN e.salaries s WHERE s.salary = :salary")
    List<Employee> findEmployeesBySalary(@Param("salary") Integer salary);

    @Query("SELECT e FROM Employee e JOIN e.salaries s JOIN e.titles t WHERE s.salary = :salary AND t.id.title = :title")
    List<Employee> findEmployeesBySalaryAndTitle(@Param("salary") Integer salary, @Param("title") String title);

    @Query("SELECT e FROM Employee e JOIN e.depEmps de JOIN de.deptNo d WHERE d.deptName = :deptName")
    List<Employee> findEmployeesByDepartmentName(@Param("deptName") String deptName);

    @Query("SELECT e FROM Employee e JOIN e.depEmps de JOIN de.deptNo d JOIN e.titles t JOIN e.salaries s WHERE d.deptName = :deptName AND t.id.title = :title AND s.salary = :salary")
    List<Employee> findEmployeesByDepartmentTitleAndSalary(@Param("deptName") String deptName, @Param("title") String title, @Param("salary") Integer salary);
}
