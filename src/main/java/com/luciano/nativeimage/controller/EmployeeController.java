package com.luciano.nativeimage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luciano.nativeimage.dto.EmployeeDto;
import com.luciano.nativeimage.dto.EmployeeDto.Create;
import com.luciano.nativeimage.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@RestController
@RequestMapping(value = "/employees/v1/crud")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;


    @PostMapping(path = "/new-employee")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Validated(Create.class) EmployeeDto employee) throws Exception {

        log.error("Received new request to insert new employee: {}", employee);
        
        EmployeeDto employeeDtoSaved = employeeService.addNewEmployee(employee);

        return new ResponseEntity<>(employeeDtoSaved, HttpStatus.CREATED);

    }



    @GetMapping(value = "/{employeeId}")
    public Object getEmployee(@RequestParam String param) {
        


        // call some service   
        return new Object();
    
    }
    


    @PutMapping(value = "/{employeeId}")
    public Object updateEmployee(@PathVariable String id, @RequestBody Object entity) {
        //TODO: process PUT request
        
        return entity;
    }



    @DeleteMapping(value = "/{employeeId}")
    public Object deleteEmployee () {
        return null;

    }


}
