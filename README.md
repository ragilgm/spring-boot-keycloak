this is sample integration spring boot with keycloak

currently i work with version 24.0.4
java version 17


step by step setup keycloak 

1. download keycloak from this link
   https://www.keycloak.org/downloads

2. run keycloak with command 
  `./bin/kc.batch start-dev` run in dev environment

3. setup username and password for admin

4. create new realm example myrealm
![img.png](img.png)

5. create client for example myclient
![img_1.png](img_1.png)
![img_2.png](img_2.png)
![img_3.png](img_3.png)
6. create role from the client roles 
![img_4.png](img_4.png)
7. create realm role 
![img_5.png](img_5.png)
add attribute associated roles 
![img_6.png](img_6.png)



step by step setup spring boot

1. add this dependency

`	<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   <dependency>
   <groupId>org.keycloak</groupId>
   <artifactId>keycloak-spring-boot-starter</artifactId>
   <version>24.0.4</version>
   </dependency>`

2. add this properties adjust with your own keycloak configuration
here for example :
`
   spring.security.oauth2.client.registration.keycloak.client-id=${keycloak.client-id}
   spring.security.oauth2.client.registration.keycloak.authorization-grant-type=authorization_code
   spring.security.oauth2.client.registration.keycloak.scope=openid
   spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:8080/realms/${keycloak.realm}
   spring.security.oauth2.client.provider.keycloak.user-name-attribute=preferred_username
   spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/realms/${keycloak.realm}
   `
3. for other configuration please follow this project 

here i'm using integration with spring cloud config 
you can refer to this repository
https://github.com/ragilgm/example-spring-cloud-config


run all project 
1. cloud config server
2. keycloak service
3. keycloak application


try to call get token via REST API 
`curl --location 'http://localhost:8080/realms/myrealm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=04501A5C5146335780FF5C9107522F40' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=myclient' \
--data-urlencode 'username=testdong12' \
--data-urlencode 'password=testdong12' \
--data-urlencode 'client_secret=RayXdy3yMcFcnIIerlEJkmgRfsVmHRuG'`

here for example response 

{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJpSXAxeFJJaDF5TXliS0djNWVZek0xbUNfZ09pUUZITTVfd05xeVIyV0R3In0.eyJleHAiOjE3MTU3NTYzMzIsImlhdCI6MTcxNTc1NjAzMiwianRpIjoiZjMyNTgyMmEtNzdlMS00MTExLWFiMjQtNWQ0YWJjZDEyOTZlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9teXJlYWxtIiwic3ViIjoiYTg0YjIzYTItZWE4Yy00ZWI2LTk3MjQtZjhhZDVkZTBiYThjIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoibXljbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiNzFlNzFkNTUtOWRiMy00YWM3LThmOTgtMmJhMzZmMDAyODBhIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbXlyZWFsbSIsImFwcF91c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibXljbGllbnQiOnsicm9sZXMiOlsidXNlciJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjcxZTcxZDU1LTlkYjMtNGFjNy04Zjk4LTJiYTM2ZjAwMjgwYSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6InRlc3Rkb25nMTIgdGVzdGRvbmcxMiIsInByZWZlcnJlZF91c2VybmFtZSI6InRlc3Rkb25nMTIiLCJnaXZlbl9uYW1lIjoidGVzdGRvbmcxMiIsImZhbWlseV9uYW1lIjoidGVzdGRvbmcxMiIsImVtYWlsIjoidGVzdGRvbmcxMkBnbWFpbC5jb20ifQ.tJgXItx5kbxoXgyBWDSX0C5YBNZvt3PtCBcd8nRt0ZaHXDnktRFWZ8xE_EJ_o7PYlzVov_ZWmOPpBsRnccHwMeP1Gc0JcN-c7hfYggaO-vcPZAhtQJ8k_FGCbS_Ii8FMpGc-r05lg3RSzz6ajBOoqPXNMWc2H0ue408UCigHPYDFSPYfgk8WDqP_fVVbDYfdEQHWbxfa_BT6cEAUO4Kype_3wQjJsPO3gMP1w6PP2_4-8knCVIXdvGFiwGhzieHkyEAeOrkWgZ61m7iObe0eQgs5yKDXZ2lUM8WZDm403BM_aTgdCFwhZ0vvq1FWqF2i9lc8CmwPfbKKqzI6qMppPQ",
    "expires_in": 300,
    "refresh_expires_in": 1800,
    "refresh_token": "eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlMTQ5MjFmZC01ZjNkLTRhMmItYmQ4Ni04NTU1MGQ2MWUwZjIifQ.eyJleHAiOjE3MTU3NTc4MzIsImlhdCI6MTcxNTc1NjAzMiwianRpIjoiOTkzNzZkZDMtNjA0OC00YTdhLWIyMjItYjQzMjRlMjIzNjA0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9teXJlYWxtIiwiYXVkIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9teXJlYWxtIiwic3ViIjoiYTg0YjIzYTItZWE4Yy00ZWI2LTk3MjQtZjhhZDVkZTBiYThjIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6Im15Y2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6IjcxZTcxZDU1LTlkYjMtNGFjNy04Zjk4LTJiYTM2ZjAwMjgwYSIsInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjcxZTcxZDU1LTlkYjMtNGFjNy04Zjk4LTJiYTM2ZjAwMjgwYSJ9.6Z6X9EYYF-14Nf4Jg_hGYOBkM-na5V26jdNqdK1fCoNNTBcE03NDQ_SYC2lKd5EXI9Uhv37smwlKp1-FkTvXIw",
    "token_type": "Bearer",
    "not-before-policy": 0,
    "session_state": "71e71d55-9db3-4ac7-8f98-2ba36f00280a",
    "scope": "email profile"
}


for other admin rest api like register, assign role etc , you can refer to this documentation
https://www.keycloak.org/docs-api/22.0.1/rest-api/index.html

thankyou
