package com.luciano.nativeimage.utils;

import com.luciano.nativeimage.dto.EmployeeDto;


public class StringLowerizer {
    
    public static void lowerize(EmployeeDto input) {
        
        String nameToLowercase = input.getName().toLowerCase();
        input.setName(nameToLowercase);
        
        String surnameToLowercase = input.getSurname().toLowerCase();
        input.setSurname(surnameToLowercase);
        
    }

}