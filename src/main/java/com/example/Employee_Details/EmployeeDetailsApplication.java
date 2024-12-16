package com.example.Employee_Details;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeeDetailsApplication {

    public static void main(String[] args) {

        SpringApplication.run(EmployeeDetailsApplication.class, args);
    }
}
