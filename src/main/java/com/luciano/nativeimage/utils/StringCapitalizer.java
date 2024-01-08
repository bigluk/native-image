package com.luciano.nativeimage.utils;

import com.luciano.nativeimage.dto.EmployeeDto;


public class StringCapitalizer {
    
    public static void capitalize(EmployeeDto input) {
        
        String nameToUppercase = input.getName().toUpperCase();
        input.setName(nameToUppercase);
        
        String surnameToUppercase = input.getSurname().toUpperCase();
        input.setSurname(surnameToUppercase);

    }

}
