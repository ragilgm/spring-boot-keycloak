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

thankyou
