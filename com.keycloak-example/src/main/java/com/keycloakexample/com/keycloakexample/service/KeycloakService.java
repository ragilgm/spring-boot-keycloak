package com.keycloakexample.com.keycloakexample.service;


import com.keycloakexample.com.keycloakexample.dto.LoginResponseDTO;
import com.keycloakexample.com.keycloakexample.dto.UserLoginRequestDTO;
import com.keycloakexample.com.keycloakexample.dto.UserRequestDTO;
import com.keycloakexample.com.keycloakexample.enums.KeycloakGrantType;
import com.keycloakexample.com.keycloakexample.invoker.KeycloakInvoker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakService {


    private final KeycloakInvoker keycloakInvoker;
    private final TokenEncryptionService tokenEncryptionService;

    public LoginResponseDTO createUser(UserRequestDTO userRequestDTO) throws Exception {

        // get admin token
        LoginResponseDTO responseDTO = keycloakInvoker.getAccessToken(KeycloakGrantType.CLIENT_CREDENTIALS,null);
        // regis user
        keycloakInvoker.registrationUser(responseDTO.getAccessToken(),userRequestDTO);
        // get user by username
        var getUsers = keycloakInvoker.getUserByUsername(responseDTO.getAccessToken(),userRequestDTO.getUsername());

        // set password
        keycloakInvoker.setPassword(responseDTO.getAccessToken(),getUsers.get(0).getId(),userRequestDTO.getPassword());

        // generate token
        UserLoginRequestDTO loginRequestDTO = new UserLoginRequestDTO();
        loginRequestDTO.setUsername(userRequestDTO.getUsername());
        loginRequestDTO.setPassword(userRequestDTO.getPassword());

        return loginUser(loginRequestDTO);
    }

    public LoginResponseDTO loginUser(UserLoginRequestDTO requestDTO) throws Exception {
        LoginResponseDTO loginResponseDTO =  keycloakInvoker.getAccessToken(KeycloakGrantType.PASSWORD,requestDTO);
        loginResponseDTO.setAccessToken(tokenEncryptionService.encrypt(loginResponseDTO.getAccessToken()));
        loginResponseDTO.setRefreshToken(tokenEncryptionService.encrypt(loginResponseDTO.getRefreshToken()));
        return loginResponseDTO;
    }
}
