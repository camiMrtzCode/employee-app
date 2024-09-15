package com.camicode.test.employee.models.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camicode.test.employee.models.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
