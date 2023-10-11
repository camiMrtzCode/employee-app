package com.camicode.siitest.employee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.MediaType;

import com.camicode.siitest.employee.models.entity.Employee;

import net.minidev.json.JSONObject;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
	
	@Value("${server.port}")
	private int serverPort;

	@LocalServerPort
	private int localServerPort;

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	@DisplayName("Employee can be created")
	@Order(1)
	void testCreateEmployee_whenValidEmployeeDetailsProvided_returnCreatedEmployeeDetails() throws JSONException {
		JSONObject employeeDetailsRequestJson = new JSONObject();
		employeeDetailsRequestJson.put("employee_name", "Garrett Winters");
		employeeDetailsRequestJson.put("employee_salary", 170750);
		employeeDetailsRequestJson.put("employee_age", 63);
		employeeDetailsRequestJson.put("profile_image","");

		HttpHeaders headers = new HttpHeaders(null);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(employeeDetailsRequestJson.toString(), headers);
		
        ResponseEntity<Employee> createdEmployeerDetailsEntity = testRestTemplate.postForEntity("/new-employee",
                request,
                Employee.class);
        Employee createdEmployeeDetails = createdEmployeerDetailsEntity.getBody();
        
        assertEquals(HttpStatus.OK, createdEmployeerDetailsEntity.getStatusCode());
        assertEquals(employeeDetailsRequestJson.getAsString("employee_name"),
        		createdEmployeeDetails.getEmployee_name(),
                "The returned employee name is most likely incorrect");
        assertEquals(employeeDetailsRequestJson.getAsString("employee_salary"),
        		createdEmployeeDetails.getEmployee_salary(),
                "The returned employee salary is most likely incorrect");
        assertEquals(employeeDetailsRequestJson.getAsString("employee_age"),
        		createdEmployeeDetails.getEmployee_age(),
                "The returned employee age is most likely incorrect");
        assertEquals(employeeDetailsRequestJson.getAsString("profile_image"),
        		createdEmployeeDetails.getProfile_image(),
                "The returned employee profile image is most likely incorrect");
        assertFalse(createdEmployeeDetails.getId().equals(null), "Id should not be empty");
	}
	
	@Test
    @DisplayName("GET /employees")
    @Order(2)
    void testGetEmployees() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity requestEntity = new HttpEntity(null, headers);
        ResponseEntity<List<Employee>> response = testRestTemplate.exchange("/employees",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Employee>>() {
                });

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "HTTP Status code 404 Not Found");
    }
}
