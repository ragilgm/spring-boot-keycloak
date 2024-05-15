package com.keycloakexample.com.keycloakexample.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetUserResponseDTO {
    private String id;
    private long createdTimestamp;
    private String username;
    private boolean enabled;
    private boolean totp;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> disableableCredentialTypes;
    private List<String> requiredActions;
    private int notBefore;
    private Access access; // Assuming Access is another entity class

    // Getters and setters
}

@Getter
@Setter
class Access {
    private boolean manageGroupMembership;
    private boolean view;
    private boolean mapRoles;
    private boolean impersonate;
    private boolean manage;

}