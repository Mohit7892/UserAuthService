package com.scaler.userauthservice.controlleradvices;

import com.scaler.userauthservice.dtos.UserAlreadyExistsExceptionDto;
import com.scaler.userauthservice.dtos.UserDoesNotExistsExceptionDto;
import com.scaler.userauthservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthservice.exceptions.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Global exception handler class for controllers
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<UserAlreadyExistsExceptionDto>
    handleUserAlreadyExistsException(UserAlreadyExistsException e){
        UserAlreadyExistsExceptionDto dto = new UserAlreadyExistsExceptionDto();
        dto.setEmail(e.getEmail());
        dto.setMessage("User with above email already exists!!");
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<UserDoesNotExistsExceptionDto>
    handleUserDoesNotExistsException(UserDoesNotExistException e){
        UserDoesNotExistsExceptionDto dto = new UserDoesNotExistsExceptionDto();
        dto.setMessage(e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
