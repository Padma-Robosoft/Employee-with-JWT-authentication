package com.example.Employee_Details.services;

import com.example.Employee_Details.dto.LoginResponse;
import com.example.Employee_Details.dto.RegisterResponse;
import com.example.Employee_Details.dto.UserRequest;
import com.example.Employee_Details.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<UserResponseDTO<RegisterResponse>> registerUser(UserRequest userRequest);

    ResponseEntity<UserResponseDTO<LoginResponse>> loginUser(UserRequest userRequest);
}
