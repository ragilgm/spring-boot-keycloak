package com.keycloakexample.com.keycloakexample.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {

    private String username;
    private Boolean enabled = true;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

}
