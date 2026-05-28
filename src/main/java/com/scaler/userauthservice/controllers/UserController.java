package com.scaler.userauthservice.controllers;

import com.scaler.userauthservice.dtos.UserDto;
import com.scaler.userauthservice.exceptions.UserDoesNotExistException;
import com.scaler.userauthservice.models.User;
import com.scaler.userauthservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IAuthService authService;

    //implement an api to get user by ID
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) throws UserDoesNotExistException {
       System.out.println("getting user dto for the id : "+id);
        User user = authService.getUserById(id);
        return user.getUserDto();
    }
}
