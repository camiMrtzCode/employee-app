package com.camicode.test.employee.models.service;

import org.springframework.stereotype.Service;

@Service
public class AnualSalaryCalculatorService {
	
	public Integer anualSalaryCalculator(Integer montlySalary) {
		return montlySalary * 12;
	}
	
}
