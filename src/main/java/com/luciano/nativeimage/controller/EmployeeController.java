package com.luciano.nativeimage.controller;

import java.lang.reflect.InvocationTargetException;
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
import com.luciano.nativeimage.dto.EmployeeDto.Update;
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
    public ResponseEntity<EmployeeDto> getEmployee(@RequestParam(name = "employeeId") @NotNull Long employeedId) throws EmployeeException {
        
        log.info("Received new request to retrieve employee with the id: {}", employeedId);

        EmployeeDto employeeDtoRetrieved = employeeService.retrieveEmployeeDto(employeedId);

        return new ResponseEntity<>(employeeDtoRetrieved, HttpStatus.OK);
    
    }
    


    @PutMapping(value = "/update-employee")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Validated(Update.class) EmployeeDto employeeFiledsToUpdate) throws IllegalArgumentException, IllegalAccessException, EmployeeException {
        
        log.info("Received new request to update the fields {} of the employee", employeeFiledsToUpdate);

        EmployeeDto employeeDtoUpdated = employeeService.updateEmployeeDto(employeeFiledsToUpdate);
        
        return new ResponseEntity<>(employeeDtoUpdated, HttpStatus.OK);
    }



    @DeleteMapping(value = "/delete-employee")
    public Object deleteEmployee (@RequestParam(name = "employeeId") @NotNull Long employeeId) {
        
        log.info("Received new request to delete employee with the id: {}", employeeId);

        employeeService.deleteEmployee(employeeId);
        
        return new ResponseEntity<>(HttpStatus.OK);

    }



    @PutMapping(value = "/refactor-employee-detail")
    public ResponseEntity<EmployeeDto> refactorEmployeeDetail(@RequestParam(name = "employeeId") @NotNull Long employeeId, 
                                                              @RequestParam(name = "classOfRefactor") @NotNull String classOfRefactor,
                                                              @RequestParam(name = "typeOfRefactor") @NotNull String typeOfRefactor) throws IllegalArgumentException, IllegalAccessException, EmployeeException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        
        log.info("Received new request to refactor ({}) details of the employee with the id: {}", typeOfRefactor , employeeId);

        EmployeeDto employeeDtoRefactored = employeeService.refactorEmployeeDtoDetails(employeeId, classOfRefactor, typeOfRefactor);
        
        return new ResponseEntity<>(employeeDtoRefactored, HttpStatus.OK);
    
    }




}
