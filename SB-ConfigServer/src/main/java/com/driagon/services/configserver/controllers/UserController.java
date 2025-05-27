package com.driagon.services.configserver.controllers;

import com.driagon.services.configserver.dto.requests.UpdateUserRequest;
import com.driagon.services.configserver.dto.requests.UserRequest;
import com.driagon.services.configserver.dto.responses.UserResponse;
import com.driagon.services.configserver.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;

    @GetMapping
    public ResponseEntity<Set<UserResponse>> getAllUsers() {
        log.info("Retrieving all users");
        Set<UserResponse> users = this.service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        log.info("Retrieving user with email {}", email);
        UserResponse user = this.service.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Creating user with email {}", userRequest.getEmail());
        UserResponse createdUser = this.service.createUser(userRequest);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String email, @Valid @RequestBody UpdateUserRequest request) {
        log.info("Updating user with email {}", email);
        UserResponse updatedUser = this.service.updateUser(email, request);
        return ResponseEntity.ok(updatedUser);
    }
}