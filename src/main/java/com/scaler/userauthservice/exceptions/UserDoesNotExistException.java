package com.scaler.userauthservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDoesNotExistException extends  Exception {
    private String message;

    public UserDoesNotExistException(String message){
        super(message);
        this.message = message;
    }
}
