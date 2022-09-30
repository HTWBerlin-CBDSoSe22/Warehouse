package com.warehouse.exception;

public class WrongCSVHeaderException extends IllegalArgumentException {
    public WrongCSVHeaderException(String message) {
        super(message);
    }

}
