package com.keycloakexample.com.keycloakexample.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SetPasswordRequestDTO {

    private String type = "password";
    private Boolean temporary = false;
    private String value;

}
