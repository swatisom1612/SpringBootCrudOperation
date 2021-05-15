package com.java.springcrud.controller;

import com.java.springcrud.model.Employee;
import com.java.springcrud.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/v1/employee")
public class Controller {
    public static final Logger logger = LoggerFactory.getLogger(Controller.class);
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllEmployee() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        return new ResponseEntity(employeeService.saveEmployee(employee), HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getAll", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Employee>> findAll(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                                  @RequestParam(required = false, defaultValue = "30") int pageSize,
                                                  @RequestParam(required = false, defaultValue = "firstName") String sortField,
                                                  @RequestParam(required = false, defaultValue = "asc") String sortDir) {
        return new ResponseEntity<>(employeeService.findPaginated(pageNo, pageSize, sortField, sortDir), HttpStatus.OK);
    }

}