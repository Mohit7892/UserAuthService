package com.scaler.userauthservice.exceptions;

public class UserAlreadyExistsException extends Exception {
    private String email;
    private String message;

    public UserAlreadyExistsException(String message, String email){
        super(message);
        this.message = message;
        this.email = email;
    }
}
