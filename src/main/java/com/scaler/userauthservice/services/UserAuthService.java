package com.scaler.userauthservice.services;

import com.scaler.userauthservice.dtos.UserTokenDto;
import com.scaler.userauthservice.exceptions.InvalidPasswordException;
import com.scaler.userauthservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthservice.exceptions.UserDoesNotExistException;
import com.scaler.userauthservice.models.Role;
import com.scaler.userauthservice.models.Session;
import com.scaler.userauthservice.models.State;
import com.scaler.userauthservice.models.User;
import com.scaler.userauthservice.repositories.RoleRepository;
import com.scaler.userauthservice.repositories.SessionRepository;
import com.scaler.userauthservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class UserAuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecretKey secretKey;

    @Override
    public User signup(String username, String email, String password) throws UserAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent())
            throw new UserAlreadyExistsException("User with this email already exists!!",
                    email);
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setEmail(email);
        //This is not a recommended way of storing password.
        // Use encryption newUser.setPassword(password);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        newUser.setPasswordHash(encryptedPassword);

        newUser.setState(State.ACTIVE);

        /*this can be done in auto from DB itself*/
        //newUser.setCreatedAt(System.currentTimeMillis());
        //newUser.setUpdatedAt(System.currentTimeMillis());

        userRepository.save(newUser);

        //set the roles
        Optional<Role> optionalRole = roleRepository.findByRoleName("DEFAULT");
        Role roleToBeSet = null;

        if(optionalRole.isEmpty()){
            Role newRole = new Role();
            newRole.setRoleName("DEFAULT");
            newRole.setState(State.ACTIVE);

            //save the new role in DB
            roleRepository.save(newRole);
            roleToBeSet = newRole;
        }
        else
            roleToBeSet = optionalRole.get();

        //set role for the new user
        newUser.setRoles(List.of(roleToBeSet));
        return newUser;
    }

    @Override
    public UserTokenDto login(String email, String password) throws UserDoesNotExistException, InvalidPasswordException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new UserDoesNotExistException("User with email "+email+" does not exists!!");

        User user = optionalUser.get();
        //check the password of the user

        //matches -> apply the salt and cold factors on the raw password and
        //then matches with the encoded pwd stored at the DB

        if (bCryptPasswordEncoder.matches(password, user.getPasswordHash())){
            //if password is correct, generate a jwt token
            Map<String, Object> payload = new HashMap<>();
            long currentTime = System.currentTimeMillis();
            payload.put("iat",currentTime);
            payload.put("exp",currentTime+1000000);
            payload.put("iss","ecomm");
            payload.put("userId", user.getId());
            payload.put("scope",user.getRoles());

            /* key is now generated as a bean from config class
            //Specify the encryption algo
            //MacAlgorithm macAlgorithm = Jwts.SIG.HS256;
            //get the secuity key
            //SecretKey secretKey = macAlgorithm.key().build();
            //generate the token with payload(claim) and sign with security key

             */
            String token = Jwts.builder().claims(payload).signWith(secretKey).compact();

            //store the token in session in DB for future validations
            Session session = new Session();
            session.setUser(user);
            session.setToken(token);
            session.setState(State.ACTIVE);

            //store/save the session
            sessionRepository.save(session);
            return new UserTokenDto(user,token);
        }
        else
            throw new InvalidPasswordException("Password entered is incorrect!!");
        //auto set by code/DB
        //user.setCreatedAt(System.currentTimeMillis());
        //user.setUpdatedAt(System.currentTimeMillis());
    }

    public boolean validateToken(String token){
        //lets first verify token from sessions table
        Optional<Session> optionalSession = sessionRepository.findByToken(token);
        if (optionalSession.isEmpty())
            return false;
        else {
            //then lets validate the token using jwt parser
            JwtParser jwtParser =Jwts.parser().verifyWith(secretKey).build();
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();

            //internally, since a signature of the jwt token cannot be
            //re-hashed. So parseSignedClaims() method actually sign the
            //payload again with the same key and verifies with already existing
            //signature. If true returns the payload otherwise throw exception

            //Now, also check if the token is active or expired
            long expiry = (long)claims.get("exp");
            long currentMoment = System.currentTimeMillis();
            if(expiry < currentMoment){
                //token is already expired, set the info in the session table
                Session session = optionalSession.get();;
                session.setState(State.INACTIVE);
                sessionRepository.save(session);
                return false;
            }
            else
                return true;
        }
    }
}
