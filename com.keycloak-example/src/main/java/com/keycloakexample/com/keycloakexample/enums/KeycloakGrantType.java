package com.keycloakexample.com.keycloakexample.enums;

import lombok.Getter;

public enum KeycloakGrantType {

    CLIENT_CREDENTIALS("client_credentials"),PASSWORD("password");

    @Getter
    private String value;


    KeycloakGrantType(String value) {
        this.value=value;
    }
}
