package com.luciano.nativeimage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee {
    
    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "name")
    private String employeeName;

    @Column(name = "surname")
    private String employeeSurname;

    @Column(name = "code")
    private String employeeCode;

    @Column(name = "department")
    private String department;

}
