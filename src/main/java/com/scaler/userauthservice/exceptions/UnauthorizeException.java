package com.scaler.userauthservice.exceptions;

public class UnauthorizeException extends Exception {
    private String message;

    public UnauthorizeException(String message){
        super(message);
        this.message = message;
    }
}
