package com.scaler.userauthservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "sessions")
@Getter
@Setter
public class Session extends  BaseModel {
    private String token;

    @ManyToOne
    private User user;


}
