package com.driagon.services.configserver.controllers;

import com.driagon.services.configserver.constants.ValidationMessages;
import com.driagon.services.configserver.dto.requests.CreateScopeRequest;
import com.driagon.services.configserver.dto.responses.CreateScopeResponse;
import com.driagon.services.configserver.dto.responses.ScopeResponse;
import com.driagon.services.configserver.services.IScopeService;
import com.driagon.services.error.exceptions.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/scopes")
@RequiredArgsConstructor
@Validated
public class ScopeController {

    private final IScopeService scopeService;

    @GetMapping
    public ResponseEntity<Set<ScopeResponse>> getScopes() {
        Set<ScopeResponse> scopes = this.scopeService.getAllScopes();

        if (scopes.isEmpty()) {
            throw new NotFoundException("No scopes found in the system.");
        }

        return ResponseEntity.ok(scopes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScopeResponse> getScopeById(
            @PathVariable
            @NotBlank(message = ValidationMessages.GetScopeById.ID_NOT_BLANK)
            @Digits(integer = 10, fraction = 0, message = ValidationMessages.GetScopeById.ID_DIGITS)
            @Positive(message = ValidationMessages.GetScopeById.ID_POSITIVE)
            String id) {
        ScopeResponse scope = this.scopeService.getScopeById(Long.valueOf(id));
        return ResponseEntity.ok(scope);
    }

    @PostMapping
    public ResponseEntity<CreateScopeResponse> createScope(@RequestBody CreateScopeRequest request) {
        CreateScopeResponse scope = this.scopeService.createScope(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(scope);
    }
}