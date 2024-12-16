package com.example.Employee_Details.services;

import com.example.Employee_Details.dto.EmployeeRequestDTO;
import com.example.Employee_Details.dto.EmployeeResponseDTO;
import com.example.Employee_Details.exception.EmptyEmployeeListException;
import com.example.Employee_Details.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeServices {

    ResponseEntity<EmployeeResponseDTO> getAllEmployeeDetail() throws EmptyEmployeeListException;

    ResponseEntity<EmployeeResponseDTO> getEmployeeById(Long id) throws NotFoundException;

    ResponseEntity<EmployeeResponseDTO> addEmployee(EmployeeRequestDTO employeeRequestDTO);

    ResponseEntity<EmployeeResponseDTO> updateEmployee(EmployeeRequestDTO employee, Long id) throws NotFoundException;

    ResponseEntity<EmployeeResponseDTO> deleteEmployee(Long id) throws NotFoundException;

    ResponseEntity<EmployeeResponseDTO> getFirstThreeEmployee() throws EmptyEmployeeListException;
}
