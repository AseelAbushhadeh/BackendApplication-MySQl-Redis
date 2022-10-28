package com.example.demo.Employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@repository=@component
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {



}
