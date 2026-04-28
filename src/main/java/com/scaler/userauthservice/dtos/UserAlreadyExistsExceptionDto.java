package com.scaler.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExistsExceptionDto {
    private String email;
    private String message;
}
