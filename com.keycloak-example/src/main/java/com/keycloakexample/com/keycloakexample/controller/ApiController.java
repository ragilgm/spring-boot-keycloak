package com.keycloakexample.com.keycloakexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/admin")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/user")
    public String privateEndpoint() {
        return "This is a private endpoint";
    }



}