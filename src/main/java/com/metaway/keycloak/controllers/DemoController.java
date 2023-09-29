package com.metaway.keycloak.controllers;

import com.metaway.keycloak.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/demo")
public class DemoController {

    @Autowired
    private AuthService authService;

    @PreAuthorize("hasRole('client_user')")
    @GetMapping
    public String hello(){
        return "Hello from Spring Boot & Keycloak";
    }

    @PreAuthorize("hasRole('client_admin')")
    @GetMapping(value = "/hello-2")
    public String hello2(){
        String userName = authService.authenticatedUsername();
        authService.validateSelfOrAdmin(userName);
        return "Hello from Spring Boot & Keycloak - ADMIN name: " + userName;
    }
}
