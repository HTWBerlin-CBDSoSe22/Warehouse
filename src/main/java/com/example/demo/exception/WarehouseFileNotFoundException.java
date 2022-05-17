package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "no file warehouse file found")
public class WarehouseFileNotFoundException extends RuntimeException{
    public WarehouseFileNotFoundException(String message){
        super(message);
    }
}
