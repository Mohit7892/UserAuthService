package com.scaler.userauthservice.services;

import com.scaler.userauthservice.dtos.UserTokenDto;
import com.scaler.userauthservice.exceptions.InvalidPasswordException;
import com.scaler.userauthservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthservice.exceptions.UserDoesNotExistException;
import com.scaler.userauthservice.models.User;
import org.springframework.stereotype.Service;

public interface IAuthService {

    User signup(String username, String email, String password) throws UserAlreadyExistsException;
    UserTokenDto login(String email, String password) throws UserDoesNotExistException, InvalidPasswordException;
    boolean validateToken(String token);
}
