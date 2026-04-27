package com.scaler.userauthservice.services;

import com.scaler.userauthservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthservice.exceptions.UserDoesNotExistException;
import com.scaler.userauthservice.models.State;
import com.scaler.userauthservice.models.User;
import com.scaler.userauthservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserAuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User signup(String username, String email, String password) throws UserAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new UserAlreadyExistsException("User with this email already exists!!",
                    email);
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setState(State.ACTIVE);
        newUser.setCreatedAt(System.currentTimeMillis());
        newUser.setUpdatedAt(System.currentTimeMillis());
        return newUser;
    }

    @Override
    public User login(String email, String password) throws UserDoesNotExistException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new UserDoesNotExistException("User with email "+email+"does not exists!!");
        User user = new User();
        user.setUserName(optionalUser.get().getUserName());
        user.setEmail(email);
        user.setPassword(password);
        user.setState(State.ACTIVE);
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());
        return user;
    }
}
