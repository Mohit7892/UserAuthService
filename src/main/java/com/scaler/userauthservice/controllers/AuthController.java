package com.scaler.userauthservice.controllers;

import com.scaler.userauthservice.dtos.*;
import com.scaler.userauthservice.exceptions.InvalidPasswordException;
import com.scaler.userauthservice.exceptions.UnauthorizeException;
import com.scaler.userauthservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthservice.exceptions.UserDoesNotExistException;
import com.scaler.userauthservice.models.User;
import com.scaler.userauthservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserDoesNotExistException, InvalidPasswordException {
        UserTokenDto userTokenDto = authService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
        //Save the token on front end as cookie under headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.SET_COOKIE,userTokenDto.getToken());

        return ResponseEntity.ok().headers(httpHeaders)
                .body(userTokenDto.getUser().getUserDto());
    }

    @PostMapping("validate-token")
    public ResponseEntity validateToken(@RequestBody ValidateTokenDto validateTokenDto) throws UnauthorizeException {
        boolean status = authService.validateToken(validateTokenDto.getToken());
        if (status){
            return ResponseEntity.ok().build();
        }
        else
            return new ResponseEntity<>
                    (new UnauthorizeException("Token is invalid/not found"),
        HttpStatus.UNAUTHORIZED);
    }

}
