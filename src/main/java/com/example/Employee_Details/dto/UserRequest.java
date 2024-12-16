package com.example.Employee_Details.dto;

import com.example.Employee_Details.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(min = 6, message = "Password must have at least 6 characters")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*\\d.*\\d).+$",
            message = "Password must include at least 2 numbers and 1 special character"
    )
    private String password;

    private Role role;

    public UserRequest(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = (role == null) ? Role.USER : role;
    }
}
