package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason = "no components found")
public class CSVNullPointerException extends RuntimeException{
    public CSVNullPointerException(String message){
        super(message);
    }
}
