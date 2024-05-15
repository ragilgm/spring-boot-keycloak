package com.keycloakexample.com.keycloakexample.controller;


import com.keycloakexample.com.keycloakexample.dto.LoginResponseDTO;
import com.keycloakexample.com.keycloakexample.dto.UserLoginRequestDTO;
import com.keycloakexample.com.keycloakexample.dto.UserRequestDTO;
import com.keycloakexample.com.keycloakexample.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final KeycloakService keycloakService;


    @PostMapping("/registration")
    public LoginResponseDTO createUser(@RequestBody UserRequestDTO requestDTO) throws Exception {
        return keycloakService.createUser(requestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserLoginRequestDTO requestDTO) throws Exception {
        return keycloakService.loginUser(requestDTO);
    }


}
