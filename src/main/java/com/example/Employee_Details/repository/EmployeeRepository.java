package com.example.Employee_Details.repository;

import com.example.Employee_Details.model.Employee;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Cacheable("employees")
    Optional<Employee> findById(Long id);

    @Override
    @CachePut(value = "employees", key = "#result.id")
    Employee save(Employee employee);

    @CacheEvict(value = "employees", key = "#id")
    void deleteById(Long id);

}
