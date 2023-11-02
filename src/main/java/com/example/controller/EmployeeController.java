package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Employee;
import com.example.exception.ResourceNotFoundException;
import com.example.pojo.TaxDetails;
import com.example.repository.EmployeeRepository;
import com.example.service.TaxCalculationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaxCalculationService taxCalculationService;

    @PostMapping
    public ResponseEntity<Employee> storeEmployeeDetails(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/tax-deduction/{employeeId}")
    public ResponseEntity<TaxDetails> calculateTaxDeduction(@PathVariable Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        TaxDetails taxDetails = taxCalculationService.calculateTax(employee);
        return ResponseEntity.ok(taxDetails);
    }
}

