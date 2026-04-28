package com.scaler.userauthservice.controllers;

import com.scaler.userauthservice.dtos.LoginRequestDto;
import com.scaler.userauthservice.dtos.SignupRequestDto;
import com.scaler.userauthservice.dtos.UserDto;
import com.scaler.userauthservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthservice.exceptions.UserDoesNotExistException;
import com.scaler.userauthservice.models.User;
import com.scaler.userauthservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    //sign-up: localhost:8080/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) throws UserAlreadyExistsException {
        String username = signupRequestDto.getUsername();
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        User newUser = authService.signup(username,email, password);
        return new ResponseEntity<>(newUser.getUserDto(), HttpStatus.CREATED);
    }

    //login: localhost::8080/auth/login
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserDoesNotExistException {
        User user = authService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
        return new ResponseEntity<>(user.getUserDto(), HttpStatus.OK);
    }
}
