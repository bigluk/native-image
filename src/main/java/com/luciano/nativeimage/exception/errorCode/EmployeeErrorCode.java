package com.luciano.nativeimage.exception.errorCode;

import lombok.Getter;


@Getter
public enum EmployeeErrorCode {

    INVALID_FIELDS,

    EMPLOYEE_ALREADY_PRESENT,

    EMPLOYEE_NOT_FOUND,

    FAILED_TO_SAVE_EMPLOYEE;

}
