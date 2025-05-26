package com.driagon.services.configserver.controllers;

import com.driagon.services.configserver.dto.requests.UserRequest;
import com.driagon.services.configserver.dto.responses.UserResponse;
import com.driagon.services.configserver.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Creating user with email {}", userRequest.getEmail());
        UserResponse createdUser = service.createUser(userRequest);
        return ResponseEntity.ok(createdUser);
    }
}