package com.warehouse.exception;

public class WarehouseFileNotFoundException extends RuntimeException {
    public WarehouseFileNotFoundException(String message) {
        super(message);
    }
}
