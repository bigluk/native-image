package com.luciano.nativeimage.dto;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class EmployeeDto {
    
    @Null(groups = Create.class, message = "Id must be null for new employee")
    private Long Id;

    @NotNull(groups = Create.class, message = "Name cannot be null for new employee")
    private String name;

    @NotNull(groups = Create.class, message = "Surname cannot be null for new employee")
    private String surname;

    @NotNull(groups = Create.class, message = "Identification number cannot be null for new employee")
    private Long identificationNumber;

    @NotNull(groups = Create.class, message = "Department cannot be null for new employee")
    private String department;


    public interface Create {}

}
