package com.keycloakexample.com.keycloakexample.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String realm;

    private String baseUrl;

    private String clientSecret;


    private String clientId;

    private String accessTokenPath;

    private String registrationPath;

    private String setPasswordPath;

   private String getUsers;

   private String rsaPrivateKey;
   private String rsaPublicKey;
   private String ecPrivateKey;
   private String ecPublicKey;
}
