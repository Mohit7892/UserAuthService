package com.scaler.userauthservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "roles")
public class Role extends BaseModel {

    //list of permissions
    private String roleName;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
