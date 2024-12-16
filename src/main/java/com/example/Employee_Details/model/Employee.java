package com.example.Employee_Details.model;

import com.example.Employee_Details.dto.EmployeeRequestDTO;
import jakarta.persistence.*;
import lombok.*;

//@Builder
//@Data
@Setter
@Getter
@Table
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "designation")
    private String designation;
    @Column(name = "department")
    private String department;

    public Employee(String name, String designation, String department) {
        this.name = name;
        this.designation = designation;
        this.department = department;
    }

    public Employee(EmployeeRequestDTO employeeRequestDTO) {
        this.name = employeeRequestDTO.getName();
        this.designation = employeeRequestDTO.getDesignation();
        this.department = employeeRequestDTO.getDepartment();
    }

    public Employee() {
    }
}
