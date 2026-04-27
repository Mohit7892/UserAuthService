package com.scaler.userauthservice.exceptions;

public class UserDoesNotExistException extends  Exception {
    private String message;

    public UserDoesNotExistException(String message){
        super(message);
    }
}
