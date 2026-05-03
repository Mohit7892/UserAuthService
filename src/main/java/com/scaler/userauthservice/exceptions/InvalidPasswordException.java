package com.scaler.userauthservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidPasswordException extends  Exception {
    private String message;

    public  InvalidPasswordException(String message){
        super(message);
        this.message = message;
    }
}
