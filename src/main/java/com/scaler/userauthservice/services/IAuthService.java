package com.scaler.userauthservice.services;

import com.scaler.userauthservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthservice.exceptions.UserDoesNotExistException;
import com.scaler.userauthservice.models.User;
import org.springframework.stereotype.Service;

public interface IAuthService {

    User signup(String username, String email, String password) throws UserAlreadyExistsException;
    User login(String email, String password) throws UserDoesNotExistException;
}
