package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CSVNullPointerException extends RuntimeException{
    public CSVNullPointerException(String message){
        super(message);
    }
}
