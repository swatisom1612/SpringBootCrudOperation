package com.java.springcrud.service;

import com.java.springcrud.model.Employee;
import com.java.springcrud.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        LOGGER.info("Fetching all employees.");
        return employeeRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Employee dbEmployee = this.employeeRepository.save(employee);
        LOGGER.info("Employee Saved Successfully. (Context : First Name - {}, Last Name - {})",
                dbEmployee.getFirstName(), dbEmployee.getLastName());
        return dbEmployee;
    }

    @Override
    public Employee getEmployeeById(long id) {
        LOGGER.info("Fetching employee with id {}", id);
        Optional<Employee> optional = employeeRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            LOGGER.error("Employee not found for ID : {}", id);
            throw new RuntimeException("Employee not found for id :: " + id);
        }
    }

    @Override
    public void deleteEmployeeById(long id) {
        this.employeeRepository.deleteById(id);
        LOGGER.info("Successfully deleted employee with ID : {}", id);
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.employeeRepository.findAll(pageable);
    }
}