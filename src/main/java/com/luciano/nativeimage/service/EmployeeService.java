package com.luciano.nativeimage.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.luciano.nativeimage.dto.EmployeeDto;
import com.luciano.nativeimage.entity.Employee;
import com.luciano.nativeimage.exception.customException.EmployeeException;
import com.luciano.nativeimage.exception.errorCode.EmployeeErrorCode;
import com.luciano.nativeimage.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private ModelMapper mapper = new ModelMapper();



    public EmployeeDto addNewEmployee(EmployeeDto employeeToInsert) throws Exception {

        log.info("Starting insert operation for the employee: {}", employeeToInsert);

        throwExceptionIfEmployeeIsAlreadyPresentOnTable(employeeToInsert);

        EmployeeDto employeeSaved = performInsertingNewEmployeeOperation(employeeToInsert);

        return employeeSaved;

    }



    private void throwExceptionIfEmployeeIsAlreadyPresentOnTable (EmployeeDto employeeToInsert) throws EmployeeException {

        log.info("Check if the employee {} is already present on employee table", employeeToInsert);

        boolean employeeIsAlreadyPresent = 
                        employeeRepository.existsEmployeeByIdentificationNumberAndDepartment(employeeToInsert.getIdentificationNumber(),
                                                                                            employeeToInsert.getDepartment());

        if (employeeIsAlreadyPresent) {
            
            log.error("Employee {} is already present in employee table", employeeToInsert);
            
            throw new EmployeeException(HttpStatus.CONFLICT, EmployeeErrorCode.EMPLOYEE_ALREADY_PRESENT, 
                                        "Employee with id number " + employeeToInsert.getIdentificationNumber() 
                                        + " and department " + employeeToInsert.getDepartment() 
                                        + " is already present in employee table", null);
        }

    }



    private EmployeeDto performInsertingNewEmployeeOperation (EmployeeDto employeeToInsert) {
        
        Employee employeeToSave = mapper.map(employeeToInsert, Employee.class);

        Employee employeeSaved = employeeRepository.save(employeeToSave);

        EmployeeDto employeeSavedDto = mapper.map(employeeSaved, EmployeeDto.class);
        
        log.info("Added new employee {} to db table", employeeToInsert);

        return employeeSavedDto;
    
    }





}
