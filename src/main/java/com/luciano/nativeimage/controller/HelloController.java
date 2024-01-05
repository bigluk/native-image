package com.luciano.nativeimage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
    
    @PostMapping(value = "/employees")
    public ResponseEntity addEmployee() {

        

        return new ResponseEntity<>(null);
    }


}
