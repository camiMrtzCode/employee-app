package com.camicode.test.employee;

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

import com.camicode.test.employee.models.entity.Employee;

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

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "HTTP Status code 200 OK");
    }
}
