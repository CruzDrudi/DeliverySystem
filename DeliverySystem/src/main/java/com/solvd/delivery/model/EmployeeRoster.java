package com.solvd.delivery.model;

import com.solvd.delivery.model.abstractClasses.Employee;

import java.util.HashSet;
import java.util.Set;

public class EmployeeRoster<T extends Employee> {
    private String departmentName;
    private Set<T> activeEmployees;
    public EmployeeRoster(String departmentName) {
        this.departmentName = departmentName;
        this.activeEmployees = new HashSet<>();
    }

    public void clockInEmployee(T employee) {
        if (employee != null) {
                activeEmployees.add(employee);
        }
    }

    public Set<T> getActiveEmployees() {
        return activeEmployees;
    }
}
