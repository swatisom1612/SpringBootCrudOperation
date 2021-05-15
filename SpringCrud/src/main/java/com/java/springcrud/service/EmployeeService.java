package com.java.springcrud.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.java.springcrud.model.Employee;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmployeeById(long id);
    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}