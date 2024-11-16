package com.example.employees.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class DeptEmpId implements java.io.Serializable {
    private static final long serialVersionUID = -3818881282072325474L;
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @Column(name = "dept_no", nullable = false, length = 4)
    private String deptNo;

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public void setEmpNo(Employee empNo) {
        this.empNo = this.empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public void setDeptNo(Department deptNo) {
        this.deptNo = this.deptNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeptEmpId entity = (DeptEmpId) o;
        return Objects.equals(this.empNo, entity.empNo) &&
                Objects.equals(this.deptNo, entity.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }

}