package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class WarehouseFileNotFoundException extends RuntimeException{
    public WarehouseFileNotFoundException(String message){
        super(message);
    }
}
