package com.scaler.userauthservice.dtos;

import com.scaler.userauthservice.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenDto {

    private User user;
    private String token;

    public UserTokenDto(User user, String token){
        this.user = user;
        this.token = token;
    }
}
