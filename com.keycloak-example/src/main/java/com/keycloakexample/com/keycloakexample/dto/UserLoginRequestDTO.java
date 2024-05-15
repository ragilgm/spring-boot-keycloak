package com.keycloakexample.com.keycloakexample.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDTO {
    private String username;
    private String password;
}
