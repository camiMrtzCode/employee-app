package com.camicode.siitest.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.camicode.siitest.employee.models.entity.Employee;
import com.camicode.siitest.employee.models.jpa.EmployeeRepository;
import com.camicode.siitest.employee.models.service.AnualSalaryCalculatorService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository emplService;
	
	@Autowired
	private AnualSalaryCalculatorService anualSalaryEjb;
	
	@GetMapping("/employees")
	public List<Employee> findAll() {
		Integer anualSalary;
		Integer monthSalary;
		
		List<Employee> emplList = emplService.findAll();
		for (int i = 0; i < emplList.size(); i++) {
			if (!emplList.isEmpty()) {
				monthSalary = emplList.get(i).getEmployee_salary();
				anualSalary = anualSalaryEjb.anualSalaryCalculator(monthSalary);
				emplList.get(i).setEmployee_anual_salary(anualSalary);
			}
		}
		return emplService.findAll();
	}
	
	@GetMapping("/employee/{id}")
	public Optional<Employee> findOne(@PathVariable Integer id) {
		Optional<Employee> empl = emplService.findById(id);
		return empl;
	}
	
	@PostMapping("/new-employee")
	public ResponseEntity<Employee> newEmployee(@RequestBody Employee empl) {
		emplService.save(empl);
		getEmployeeAnualSalary(empl.id);
		return ResponseEntity.created(null).build();
	}
	
	@GetMapping("/employee/{id}/anual-salary")
	public Integer getEmployeeAnualSalary(@PathVariable Integer id) {
		Integer anualSalary = 0;
		Optional<Employee> empl = emplService.findById(id);
		if(empl.isPresent()) {
			Integer montlySalary = empl.get().getEmployee_salary();
			
			anualSalary = anualSalaryEjb.anualSalaryCalculator(montlySalary);
		}
		
		return anualSalary;
		
		
	}
}
