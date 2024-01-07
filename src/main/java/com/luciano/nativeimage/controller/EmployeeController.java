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
import com.luciano.nativeimage.exception.customException.EmployeeException;
import com.luciano.nativeimage.service.EmployeeService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@RestController
@RequestMapping(value = "/employees/v1/crud")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;


    @PostMapping(path = "/new-employee")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Validated(Create.class) EmployeeDto employee) throws Exception {

        log.info("Received new request to insert new employee: {}", employee);
        
        EmployeeDto employeeDtoSaved = employeeService.addNewEmployee(employee);

        return new ResponseEntity<>(employeeDtoSaved, HttpStatus.CREATED);

    }



    @GetMapping(path = "/retrieve-employee")
    public ResponseEntity<EmployeeDto> getEmployee(@RequestParam(name = "employeeId") Long employeedId) throws EmployeeException {
        
        log.info("Received new request to retrieve new employee with the id: {}", employeedId);

        EmployeeDto employeeDtoRetrieved = employeeService.retrieveEmployeeDto(employeedId);

        return new ResponseEntity<>(employeeDtoRetrieved, HttpStatus.OK);
    
    }
    


    @PutMapping(value = "/update-employee")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestParam(name = "employeeId") Long employeeId, @RequestBody EmployeeDto entity) {
        
        log.info("Received new request to update employee with the id: {}", employeeId);

        EmployeeDto employeeDtoUpdated = employeeService.updateEmployeeDto();
        
        return new ResponseEntity<>(employeeDtoUpdated, HttpStatus.OK);
    }



    @DeleteMapping(value = "/delete-employee")
    public Object deleteEmployee (@RequestParam(name = "employeeId") @NotNull Long employeeId) {
        
        log.info("Received new request to delete employee with the id: {}", employeeId);

        employeeService.deleteEmployee(employeeId);
        
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
