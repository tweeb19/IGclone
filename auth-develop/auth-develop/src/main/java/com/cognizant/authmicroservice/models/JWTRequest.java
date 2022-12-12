package com.cognizant.authmicroservice.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class JWTRequest implements Serializable {

    private String username;
    private String password;
}
