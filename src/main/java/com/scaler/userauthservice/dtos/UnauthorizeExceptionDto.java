package com.scaler.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizeExceptionDto {

    private String username;
    private String message;
}
