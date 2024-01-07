package com.luciano.nativeimage.exception.handler;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.luciano.nativeimage.exception.customException.EmployeeException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ControllerAdvice
public class EmployeeExceptionHandler extends ResponseEntityExceptionHandler{
    

    @ExceptionHandler(EmployeeException.class)
    protected ResponseEntity<ExceptionResponseApi> handleEmployeeException(EmployeeException ex) {
        
        log.error("Get the exception: {} with the following message: {}", ex, ex.getDescription());

        ExceptionResponseApi exceptionResponseApi = 
                                    ExceptionResponseApi.builder()
                                                        .status(ex.getHttpStatus())
                                                        .message(ex.getEmployeeErrorCode().name())
                                                        .description(ex.getDescription())                                                                
                                                        .build();

        return new ResponseEntity<>(exceptionResponseApi, ex.getHttpStatus());
        
    }


    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
                                                                  HttpHeaders headers, HttpStatusCode status, 
                                                                  WebRequest request) {
    
        log.error("Get a validation exception {} qith message {}", ex, ex.getMessage());

        BindingResult bindingResult = ex.getBindingResult();
        
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

        String resultString = "";
        if(fieldErrorList.isEmpty()){
            for(ObjectError objectError : bindingResult.getAllErrors()) {
                resultString+=objectError.getDefaultMessage()+",";
            }
        }

        ExceptionResponseApi exceptionResponseApi = 
                                ExceptionResponseApi.builder()
                                                    .status(HttpStatus.BAD_REQUEST)
                                                    .message(processErrorFields(fieldErrorList) + resultString)
                                                    .build();

        return new ResponseEntity<>(exceptionResponseApi, HttpStatus.BAD_REQUEST);

    }


    private String processErrorFields (List<FieldError> fieldErrorList) {

        StringBuilder error = new StringBuilder("Invalid Input: ");

        int count = 0;
        for(FieldError fieldError : fieldErrorList) {

            error.append(fieldError.getDefaultMessage());

            if (count < fieldErrorList.size()-1) {
                error.append(", ");
            }

            count++;
        }

        return error.toString();

    }



    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<ExceptionResponseApi> handleGenericException(Throwable ex) {
        
        log.error("Get a generic exception: {} with the following message: {}", ex, ex.getMessage());
        
        ExceptionResponseApi exceptionResponseApi = 
                                    ExceptionResponseApi.builder()
                                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                        .message("Sorry, we encountrered an internal error")
                                                        .build();

        return new ResponseEntity<>(exceptionResponseApi, HttpStatus.INTERNAL_SERVER_ERROR);
        
    }



}
