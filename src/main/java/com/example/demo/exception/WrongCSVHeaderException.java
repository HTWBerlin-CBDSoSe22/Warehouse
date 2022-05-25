package com.example.demo.exception;

public class WrongCSVHeaderException extends IllegalArgumentException{
    public WrongCSVHeaderException(String message){
        super(message);
    }

}
