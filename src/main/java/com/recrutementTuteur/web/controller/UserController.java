/*
package com.recrutementTuteur.web.controller;

import com.recrutementTuteur.services.interfaces.IUserService;
import com.recrutementTuteur.web.dto.requests.RegisterRequest;
import com.recrutementTuteur.web.dto.response.RegisterResponse;
import com.recrutementTuteur.web.dto.requests.LoginRequest;
import com.recrutementTuteur.web.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = userService.register(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}*/


package com.recrutementTuteur.web.controller;

import com.recrutementTuteur.services.interfaces.IUserService;
import com.recrutementTuteur.web.dto.requests.RegisterRequest;
import com.recrutementTuteur.web.dto.response.RegisterResponse;
import com.recrutementTuteur.web.dto.requests.LoginRequest;
import com.recrutementTuteur.web.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = userService.register(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
