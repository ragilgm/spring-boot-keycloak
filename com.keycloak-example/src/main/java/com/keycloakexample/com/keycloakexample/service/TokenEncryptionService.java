package com.keycloakexample.com.keycloakexample.service;

import com.keycloakexample.com.keycloakexample.configuration.properties.KeycloakProperties;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;

@Component
public class TokenEncryptionService {

    private PrivateKey rsaPrivateKey;
    private PublicKey rsaPublicKey;


    private final KeycloakProperties keycloakProperties;

    public  TokenEncryptionService(KeycloakProperties keycloakProperties) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.keycloakProperties = keycloakProperties;

        byte[] rsaPrivateKeyBytes = Base64.getDecoder().decode(keycloakProperties.getRsaPrivateKey());
        byte[] rsaPublicKeyBytes = Base64.getDecoder().decode(keycloakProperties.getRsaPublicKey());

        // Generate RSA private and public keys from byte arrays
        KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
        this.rsaPrivateKey = rsaKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(rsaPrivateKeyBytes));
        this.rsaPublicKey = rsaKeyFactory.generatePublic(new X509EncodedKeySpec(rsaPublicKeyBytes));

    }


    public  String encrypt(String token) throws JOSEException, ParseException
    {
        // Parse the token
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Create the JWE header
        JWEHeader jwtHeader = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);

        // Create the JWE object with the signed JWT as payload
        JWEObject jweObject = new JWEObject(jwtHeader, new Payload(signedJWT));

        // Encrypt the JWE object
        jweObject.encrypt(new RSAEncrypter((RSAPublicKey) this.rsaPublicKey));

        // Serialize to compact form
        return jweObject.serialize();
    }

    public  String decrypt(String jweString) throws JOSEException, ParseException {
        // Parse the JWE string
        JWEObject jweObject = JWEObject.parse(jweString);

        // Decrypt the JWE object
        jweObject.decrypt(new RSADecrypter(rsaPrivateKey));

        // Extract the signed JWT from the payload
        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

        if (signedJWT == null) {
            throw new IllegalStateException("The payload is not a signed JWT");
        }

        // Return the serialized signed JWT
        return signedJWT.serialize();
    }


}
