package com.keycloakexample.com.keycloakexample.filter;

import com.keycloakexample.com.keycloakexample.configuration.properties.KeycloakProperties;
import com.keycloakexample.com.keycloakexample.service.TokenEncryptionService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;

@RequiredArgsConstructor
public class TokenDecryptionFilter extends OncePerRequestFilter {


    private final TokenEncryptionService tokenEncryptionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
//
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String encryptedToken = authHeader.substring(7);
            try {
                // Decrypt the token
                String tokenDecrypt = tokenEncryptionService.decrypt(encryptedToken);

                request = new HttpServletRequestWrapper(request) {
                    @Override
                    public String getHeader(String name) {
                        if ("Authorization".equals(name)) {
                            return "Bearer " + tokenDecrypt;
                        }
                        return super.getHeader(name);
                    }
                };

            } catch (JOSEException | ParseException e) {
                e.printStackTrace();
                // Handle the decryption exception (e.g., log it, return an error response, etc.)
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}