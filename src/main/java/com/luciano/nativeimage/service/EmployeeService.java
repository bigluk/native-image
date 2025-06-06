package com.luciano.nativeimage.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
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

        EmployeeDto employeeSaved = performInsertEmployeeOperation(employeeToInsert);

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
                                        + " is already present in employee table");
        }

    }


    private EmployeeDto performInsertEmployeeOperation (EmployeeDto employeeToInsert) {
        
        Employee employeeToSave = mapper.map(employeeToInsert, Employee.class);

        Employee employeeSaved = employeeRepository.save(employeeToSave);

        EmployeeDto employeeSavedDto = mapper.map(employeeSaved, EmployeeDto.class);
        
        log.info("Added employee {} to db table", employeeToInsert);

        return employeeSavedDto;
    
    }



    public EmployeeDto retrieveEmployeeDto (Long employeeId) throws EmployeeException {

        throwExceptionIfEmployeeIdIsAnInvalidField(employeeId);

        log.info("Starting retrieve operation for the employee with employee id: {}", employeeId);

        EmployeeDto employeeDtoRetrieved = performRetrieveOperation(employeeId);

        log.info("Retrieved employee {}", employeeDtoRetrieved);

        return employeeDtoRetrieved;

    }


    private void throwExceptionIfEmployeeIdIsAnInvalidField (Long employeeId) throws EmployeeException {

        if (employeeId <= 0) {
            log.error("Cannot start retreieve operation cause employee id is: {}", employeeId);
            throw new EmployeeException(HttpStatus.BAD_REQUEST, 
                                        EmployeeErrorCode.INVALID_FIELDS, 
                                        "Employee id with value " + employeeId + " is invalid.");
        }

    }


    private EmployeeDto performRetrieveOperation (Long employeeId) throws EmployeeException {

        Optional<Employee> employeeRetrieved = employeeRepository.findById(employeeId);

        if (!employeeRetrieved.isPresent()) {
            log.error("No Employee with employee id " + employeeId + " found.");
            throw new EmployeeException(HttpStatus.NOT_FOUND, 
                                        EmployeeErrorCode.EMPLOYEE_NOT_FOUND, 
                                        "No Employee with employee id " + employeeId + " found.");
        }

        EmployeeDto employeeDtoRetrieved = mapper.map(employeeRetrieved, EmployeeDto.class);
        
        return employeeDtoRetrieved;

    }



    public EmployeeDto updateEmployeeDto (EmployeeDto employeeFieldsToUpdate) throws EmployeeException, IllegalArgumentException, IllegalAccessException {

        log.info("Starting update operation for the employee with employeeId: {}", employeeFieldsToUpdate.getId());

        throwExceptionIfEmployeeNotExist(employeeFieldsToUpdate.getId());

        EmployeeDto employeeDtoUpdated = performInsertEmployeeOperation(employeeFieldsToUpdate);

        return employeeDtoUpdated;

    }


    private void throwExceptionIfEmployeeNotExist (Long employeeId) throws EmployeeException {

        log.info("Check if the employee {} is already present on employee table", employeeId);

        boolean employeeIsAlreadyPresent = 
                        employeeRepository.existsById(employeeId);

        if (!employeeIsAlreadyPresent) {
            
            log.error("Employee with employeeId {} is not present in employee table", employeeId);
            
            throw new EmployeeException(HttpStatus.CONFLICT, EmployeeErrorCode.EMPLOYEE_NOT_FOUND, 
                                        "Employee with id number " + employeeId
                                        + " is not present in employee table");
        }


    }



    public void deleteEmployee (Long employeeId) {

        log.info("Starting delete operation for the employee with Id: {}", employeeId);

        employeeRepository.deleteById(employeeId);

    }



    public EmployeeDto refactorEmployeeDtoDetails(Long employeeId, String classOfRefactor, String typeOfRefactor) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, EmployeeException {
        
        log.info("Starting refactor operation for the employee with Id: {}", employeeId);

        checkClassAndTypeOfRefactorValidity(classOfRefactor, typeOfRefactor);

        EmployeeDto employeeDtoToRefactor = performRetrieveOperation(employeeId);

        Class<?> clazz = Class.forName("com.luciano.nativeimage.utils."+classOfRefactor);
        Method method = clazz.getDeclaredMethod(typeOfRefactor, EmployeeDto.class);
        method.invoke(null, employeeDtoToRefactor);
        
        EmployeeDto employeeDtoRefactored = performInsertEmployeeOperation(employeeDtoToRefactor);

        return employeeDtoRefactored;
    
    }


    private void checkClassAndTypeOfRefactorValidity (String classOfRefactor, String typeOfRefactor) throws EmployeeException {

        if (classOfRefactor == null || classOfRefactor.isBlank() || classOfRefactor.isEmpty() 
            || !(classOfRefactor.equals("StringCapitalizer") || classOfRefactor.equals("StringLowerizer"))) {
            
            log.error("Action not performed cause classOfRefactor is: {}", classOfRefactor);
            throw new EmployeeException(HttpStatus.BAD_REQUEST, EmployeeErrorCode.ACTION_NOT_SUPPORTED, classOfRefactor +  " not supported");    
        }

        if (typeOfRefactor == null || typeOfRefactor.isBlank() || typeOfRefactor.isEmpty() 
            || !(typeOfRefactor.equals("capitalize") || typeOfRefactor.equals("lowerize"))) {
            
            log.error("Action not performed cause typeOfRefactor is: {}", typeOfRefactor);
            throw new EmployeeException(HttpStatus.BAD_REQUEST, EmployeeErrorCode.ACTION_NOT_SUPPORTED, typeOfRefactor +  " not supported");    
        }

    }



}
