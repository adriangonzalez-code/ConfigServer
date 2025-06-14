package com.driagon.services.configserver.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(UserDetails userDetails);
}