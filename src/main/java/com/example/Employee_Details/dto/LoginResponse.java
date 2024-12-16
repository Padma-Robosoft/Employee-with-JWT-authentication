package com.example.Employee_Details.dto;

import com.example.Employee_Details.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String email;
    private Role role;
    private String token;
}
