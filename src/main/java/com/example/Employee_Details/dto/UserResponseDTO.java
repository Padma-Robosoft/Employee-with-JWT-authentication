package com.example.Employee_Details.dto;

import lombok.Getter;

@Getter
public class UserResponseDTO<T> {
    private int status;
    private int code;
    private String message;
    private T data;

    public UserResponseDTO(int status, int code, String message, T userResponse) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = userResponse;
    }
}
