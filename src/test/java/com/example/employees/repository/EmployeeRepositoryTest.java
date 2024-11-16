package com.example.employees.repository;

import com.example.employees.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application.properties")
class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepEmpRepository depEmpRepository;

    private Employee employee;
    private Department department;
    private Integer employeeId;

    @BeforeEach
    void setUp() {
        entityManager.clear(); // Clear the persistence context

        employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setGender("Male");
        employee.setBirthDate(LocalDate.of(1971, 10, 25));
        employee.setHireDate(LocalDate.of(2022, 1, 2));
        entityManager.persistAndFlush(employee);
        employeeId = employee.getId();

        department = new Department();
        department.setDeptNo("d001");
        department.setDeptName("Engineering");
        entityManager.persistAndFlush(department);

        DeptEmpId depEmpId = new DeptEmpId();
        depEmpId.setEmpNo(employeeId);
        depEmpId.setDeptNo("d001");

        DeptEmp depEmp = new DeptEmp();
        depEmp.setId(depEmpId);
        depEmp.setEmpNo(employee);
        depEmp.setDeptNo(department);
        depEmp.setFromDate(LocalDate.of(2022, 1, 2));
        depEmp.setToDate(LocalDate.of(2023, 1, 2));
        entityManager.persistAndFlush(depEmp);

        Salary salary = new Salary();
        SalaryId salaryId = new SalaryId();
        salaryId.setEmpNo(employeeId);
        salaryId.setFromDate(LocalDate.of(2022, 1, 2));
        salary.setId(salaryId);
        salary.setEmpNo(employee);
        salary.setSalary(100000);
        salary.setToDate(LocalDate.of(2023, 1, 2));
        employee.setSalaries(List.of(salary));
        entityManager.persistAndFlush(salary);

        Title title = new Title();
        TitleId titleId = new TitleId();
        titleId.setEmpNo(employeeId);
        titleId.setFromDate(LocalDate.of(2022, 1, 2));
        title.setId(titleId);
        title.setEmpNo(employee);
        title.getId().setTitle("Engineer");
        title.setToDate(LocalDate.of(2023, 1, 2));
        entityManager.persistAndFlush(title);

        employee.setSalaries(List.of(salary));
        employee.setTitles(List.of(title));
        entityManager.persistAndFlush(employee);
    }

    @Test
    void findByFirstName() {
        Iterable<Employee> employees = employeeRepository.findByFirstName("John");
        assertTrue(employees.iterator().hasNext());
    }

    @Test
    void testFindEmployeesByDepartmentName() {
        List<Employee> employees = employeeRepository.findEmployeesByDepartmentName("Engineering");

        assertFalse(employees.isEmpty());
        assertEquals("John", employees.get(0).getFirstName());
    }

    @Test
    void testFindEmployeesBySalary() {
        List<Employee> employees = employeeRepository.findEmployeesBySalary(100000);

        assertFalse(employees.isEmpty());
        assertEquals("John", employees.get(0).getFirstName());
        assertEquals(Integer.valueOf(100000), employees.get(0).getSalaries().get(0).getSalary());
    }

    @Test
    void testFindEmployeesByTitle() {
        List<Employee> employees = employeeRepository.findEmployeesByTitle("Engineer");

        assertFalse(employees.isEmpty());
        assertEquals("John", employees.get(0).getFirstName());
        assertEquals("Engineer", employees.get(0).getTitles().get(0).getId().getTitle());
    }

    @Test
    void testFindEmployeesBySalaryAndTitle() {
        List<Employee> employees = employeeRepository.findEmployeesBySalaryAndTitle(100000,"Engineer");

        assertFalse(employees.isEmpty());
        assertEquals("John", employees.get(0).getFirstName());
        assertEquals(Integer.valueOf(100000), employees.get(0).getSalaries().get(0).getSalary());
        assertEquals("Engineer", employees.get(0).getTitles().get(0).getId().getTitle());
    }

    @Test
    void testJoinQuery() {
        List<Employee> employees = entityManager.getEntityManager()
                .createQuery("SELECT e FROM Employee e JOIN e.depEmps de JOIN de.deptNo d WHERE d.deptName = :departmentName", Employee.class)
                .setParameter("departmentName", "Engineering")
                .getResultList();

        assertFalse(employees.isEmpty());
        assertEquals("John", employees.get(0).getFirstName());
    }

    @Test
    void findByFirstName_NotFound() {
        Iterable<Employee> employees = employeeRepository.findByFirstName("Jane");
        assertFalse(employees.iterator().hasNext());
    }

    @Test
    void findByLastName() {
        Iterable<Employee> employees = employeeRepository.findByLastName("Doe");
        assertTrue(employees.iterator().hasNext());
    }

    @Test
    void findByLastName_NotFound() {
        Iterable<Employee> employees = employeeRepository.findByLastName("Smith");
        assertFalse(employees.iterator().hasNext());
    }

    @Test
    void findAll() {
        Iterable<Employee> employees = employeeRepository.findAll();
        assertTrue(employees.iterator().hasNext());
    }

    @Test
    void findById() {
        Optional<Employee> foundEmployee = employeeRepository.findById(employee.getId());
        assertTrue(foundEmployee.isPresent());
    }

    @Test
    void findById_NotFound() {
        Optional<Employee> foundEmployee = employeeRepository.findById(-1);
        assertFalse(foundEmployee.isPresent());
    }

    @Test
    void findByGender() {
        List<Employee> employees = employeeRepository.findByGender("Male");
        assertFalse(employees.isEmpty());
    }

    @Test
    void findByGender_NotFound() {
        List<Employee> employees = employeeRepository.findByGender("Female");
        assertTrue(employees.isEmpty());
    }
}