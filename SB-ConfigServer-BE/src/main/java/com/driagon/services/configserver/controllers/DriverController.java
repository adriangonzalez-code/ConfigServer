package com.driagon.services.configserver.controllers;

import com.driagon.services.configserver.dto.responses.SetPropertyResponse;
import com.driagon.services.configserver.services.IPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/driver")
@RequiredArgsConstructor
public class DriverController {

    private final IPropertyService propertyService;

    @GetMapping("/properties")
    public ResponseEntity<Set<SetPropertyResponse>> getAllPropertiesByScope(@RequestHeader("scopeName") String scopeName, @RequestHeader("accessKey") String accessKey) {
        Set<SetPropertyResponse> properties = propertyService.getAllPropertiesAndSecretDecryptedByScopeNameAndAccessKey(scopeName, accessKey);
        return ResponseEntity.ok(properties);
    }
}