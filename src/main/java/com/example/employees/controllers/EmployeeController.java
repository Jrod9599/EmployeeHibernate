package com.example.employees.controllers;

import com.example.employees.exceptions.ResourceNotFoundException;
import com.example.employees.models.Employee;
import com.example.employees.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    // Get All Employees
    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get a Single Employee
    @GetMapping("/getEmployee/{id}")
    public Employee getNoteById(@PathVariable(value = "id") Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
    }

    // Create a new Employee
    @PostMapping("/newEmployee")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // Update a Employee
    @PutMapping("/updateEmployee/{id}")
    public Employee updateEmployee(@PathVariable(value = "id") Long employeeId,
                           @Valid @RequestBody Employee employeeDetails) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setTitle(employeeDetails.getTitle());

        Employee updatedNote = employeeRepository.save(employee);
        return updatedNote;
    }

    // Delete an Employee
    @DeleteMapping("/removeEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        employeeRepository.delete(employee);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "Hello AWS, Docker Tomcat, MySQL";
    }


}
