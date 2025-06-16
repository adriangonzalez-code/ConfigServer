package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.responses.AuthResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-16T12:45:09-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl extends AuthMapper {

    @Override
    public AuthResponse mapTokenToAuthResponse(String token) {
        if ( token == null ) {
            return null;
        }

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();

        authResponse.accessToken( token );

        return authResponse.build();
    }
}
