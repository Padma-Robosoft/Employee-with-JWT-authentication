package com.example.Employee_Details.exception;

import com.example.Employee_Details.dto.EmployeeResponseDTO;
import com.example.Employee_Details.dto.UserResponseDTO;
import com.example.Employee_Details.Utils.ResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Log4j2
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<EmployeeResponseDTO> handleNotFoundException(NotFoundException exception) {
        return ResponseUtil.errorResponseDefault(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {EmptyEmployeeListException.class})
    public ResponseEntity<EmployeeResponseDTO> handleEmptyEmployeeListException(EmptyEmployeeListException exception) {
        return ResponseUtil.errorResponseDefault(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new UserResponseDTO<>(-1, 500, "Internal Server Error: " + ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException  ex) {
        Map<String, String> errors = new HashMap<>();
        // Extract field errors
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(new UserResponseDTO<>(-1, 400, "Validation error occurred", errors), HttpStatus.BAD_REQUEST);
    }

}
