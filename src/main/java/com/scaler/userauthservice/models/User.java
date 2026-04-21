package com.scaler.userauthservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name = "users")
public class User extends BaseModel {

    private String userName;
    private String emailId;
    private String password;

    @ManyToMany
    private List<Role> roles;
}
