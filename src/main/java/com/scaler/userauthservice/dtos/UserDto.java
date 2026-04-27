package com.scaler.userauthservice.dtos;

import com.scaler.userauthservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private long Id;
    private String name;
    private String email;
    private List<Role> roles;
}
