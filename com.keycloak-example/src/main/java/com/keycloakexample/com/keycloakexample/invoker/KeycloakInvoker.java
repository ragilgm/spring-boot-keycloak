package com.keycloakexample.com.keycloakexample.invoker;


import com.keycloakexample.com.keycloakexample.configuration.properties.KeycloakProperties;
import com.keycloakexample.com.keycloakexample.dto.*;
import com.keycloakexample.com.keycloakexample.enums.KeycloakGrantType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakInvoker {

    private final KeycloakProperties keycloakProperties;
    private static final String PATH_ACCESS_TOKEN = "/admin/realms/%s/users";
    private final RestTemplate restTemplate;

    /*
    * KeycloakGrantType CLIENT_CREDENTIALS for login as admin
    * KeycloakGrantType PASSWORD for login as user
    * UserLoginRequestDTO mandatory if KeycloakGrantType PASSWORD
    * */
    public LoginResponseDTO getAccessToken(@NonNull KeycloakGrantType keycloakGrantType, UserLoginRequestDTO loginRequestDTO) throws Exception {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", keycloakGrantType.getValue());
        body.add("client_id", keycloakProperties.getClientId());
        body.add("client_secret", keycloakProperties.getClientSecret());


        if(Objects.equals(keycloakGrantType,KeycloakGrantType.PASSWORD)){

            if(Objects.isNull(loginRequestDTO)){
                throw new Exception("Invalid argument");
            }

            body.add("username",loginRequestDTO.getUsername());
            body.add("password",loginRequestDTO.getPassword());
        }

        var request = new HttpEntity<>(body, null);



        try {
            var response = restTemplate.postForEntity(keycloakProperties.getBaseUrl()+String.format(keycloakProperties.getAccessTokenPath(),keycloakProperties.getRealm()),request, LoginResponseDTO.class);
            log.info("login success");
            return response.getBody();
        }catch (Exception e){
            log.error("error while trying to login , detail ",e);
            throw e;
        }
    }


    public void registrationUser(String adminAccessToken, @NonNull UserRequestDTO requestDTO) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+adminAccessToken);

        KeyCloakRegisterRequestDTO regis = new KeyCloakRegisterRequestDTO();
        regis.setEmail(requestDTO.getEmail());
        regis.setEnabled(requestDTO.getEnabled());
        regis.setUsername(requestDTO.getUsername());
        regis.setFirstName(requestDTO.getFirstName());
        regis.setLastName(requestDTO.getLastName());

        var request = new HttpEntity<>(regis, headers);

        try {
            var response = restTemplate.postForEntity(keycloakProperties.getBaseUrl()+String.format(keycloakProperties.getRegistrationPath()),request, String.class);
            log.info("registration success");
            response.getBody();
        }catch (Exception e){
            log.error("error while trying to login , detail ",e);
            throw e;
        }
    }

    public void setPassword(String accessToken,String userId, String password) {
        var requestDTO = new SetPasswordRequestDTO();
        requestDTO.setValue(password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+accessToken);
        var request = new HttpEntity<>(requestDTO, headers);
        try {
            var url = keycloakProperties.getBaseUrl()+String.format(keycloakProperties.getSetPasswordPath(),userId);
            restTemplate.put(url,request, String.class);
            log.info("registration success");
        }catch (Exception e){
            log.error("error while trying to login , detail ",e);
            throw e;
        }
    }

    public List<GetUserResponseDTO> getUserByUsername(String adminAccessToken, String username) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+adminAccessToken);
        var request = new HttpEntity<>(null, headers);

        var url =keycloakProperties.getBaseUrl()+String.format(keycloakProperties.getGetUsers(),username);
        try {
            ResponseEntity<List<GetUserResponseDTO>> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, new ParameterizedTypeReference<List<GetUserResponseDTO>>() {});
            System.out.println(response);
            return response.getBody();
        }catch (Exception e){
            log.error("error while get user by username, detail ",e);
        }
        return null;

    }
}
