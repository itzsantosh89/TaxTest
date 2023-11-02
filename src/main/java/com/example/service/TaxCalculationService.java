package com.example.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.entity.Employee;
import com.example.pojo.TaxDetails;

@Service
public class TaxCalculationService {
    public TaxDetails calculateTax(Employee employee) {
        TaxDetails taxDetails = new TaxDetails();
        taxDetails.setEmployeeId(employee.getEmployeeId());
        taxDetails.setFirstName(employee.getFirstName());
        taxDetails.setLastName(employee.getLastName());

        LocalDate currentDate = LocalDate.now();
        LocalDate joiningDate = employee.getDoj();
        int monthsWorked = Math.max(0, (int) java.time.temporal.ChronoUnit.MONTHS.between(joiningDate, currentDate));
        double totalSalary = (employee.getSalary() / 30) * monthsWorked;
        taxDetails.setYearlySalary(totalSalary * 12);

        double taxAmount = 0;
        if (taxDetails.getYearlySalary() <= 250000) {
            taxAmount = 0;
        } else if (taxDetails.getYearlySalary() <= 500000) {
            taxAmount = (taxDetails.getYearlySalary() - 250000) * 0.05;
        } else if (taxDetails.getYearlySalary() <= 1000000) {
            taxAmount = 25000 + (taxDetails.getYearlySalary() - 500000) * 0.10;
        } else {
            taxAmount = 125000 + (taxDetails.getYearlySalary() - 1000000) * 0.20;
        }

        taxDetails.setTaxAmount(taxAmount);

        double cessAmount = 0;
        if (taxDetails.getYearlySalary() > 2500000) {
            cessAmount = (taxDetails.getYearlySalary() - 2500000) * 0.02;
        }

        taxDetails.setCessAmount(cessAmount);

        return taxDetails;
    }
}

