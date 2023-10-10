package com.camicode.siitest.employee.models.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camicode.siitest.employee.models.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
