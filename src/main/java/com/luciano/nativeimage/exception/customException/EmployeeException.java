package com.luciano.nativeimage.exception.customException;

import org.springframework.http.HttpStatus;
import com.luciano.nativeimage.exception.errorCode.EmployeeErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class EmployeeException extends Exception {
    
    private HttpStatus httpStatus;

    private EmployeeErrorCode employeeErrorCode;

    private String description;

    private Throwable cause;


    public EmployeeException(HttpStatus httpStatus, 
                            EmployeeErrorCode employeeErrorCode, 
                            String description) {

        this.httpStatus=httpStatus;
        this.employeeErrorCode=employeeErrorCode;
        this.description=description;
    }

}
