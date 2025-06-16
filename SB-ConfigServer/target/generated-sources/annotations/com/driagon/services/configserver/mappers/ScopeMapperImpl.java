package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.requests.CreateScopeRequest;
import com.driagon.services.configserver.dto.responses.CreateScopeResponse;
import com.driagon.services.configserver.dto.responses.ScopeResponse;
import com.driagon.services.configserver.entities.Scope;
import com.driagon.services.configserver.entities.User;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-16T12:34:18-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class ScopeMapperImpl implements ScopeMapper {

    @Override
    public Set<ScopeResponse> mapScopeEntitiesToScopeResponses(Set<Scope> scopes) {
        if ( scopes == null ) {
            return null;
        }

        Set<ScopeResponse> set = new LinkedHashSet<ScopeResponse>( Math.max( (int) ( scopes.size() / .75f ) + 1, 16 ) );
        for ( Scope scope : scopes ) {
            set.add( mapScopeEntityToScopeResponse( scope ) );
        }

        return set;
    }

    @Override
    public ScopeResponse mapScopeEntityToScopeResponse(Scope scope) {
        if ( scope == null ) {
            return null;
        }

        ScopeResponse.ScopeResponseBuilder scopeResponse = ScopeResponse.builder();

        scopeResponse.id( scope.getId() );
        scopeResponse.scopeName( scope.getName() );
        scopeResponse.description( scope.getDescription() );
        scopeResponse.createdBy( scopeCreatedByEmail( scope ) );
        if ( scope.getCreatedAt() != null ) {
            scopeResponse.createdAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( scope.getCreatedAt() ) );
        }

        scopeResponse.users( scope.getUsers().stream().map(user -> user.getEmail()).collect(java.util.stream.Collectors.toSet()) );

        return scopeResponse.build();
    }

    @Override
    public Scope mapCreateScopeRequestToScopeEntity(CreateScopeRequest request) {
        if ( request == null ) {
            return null;
        }

        Scope.ScopeBuilder scope = Scope.builder();

        scope.name( request.getScopeName() );
        scope.description( request.getDescription() );

        scope.accessKey( java.util.UUID.randomUUID().toString() );

        return scope.build();
    }

    @Override
    public CreateScopeResponse mapScopeEntityToCreateScopeResponse(Scope scope) {
        if ( scope == null ) {
            return null;
        }

        CreateScopeResponse.CreateScopeResponseBuilder createScopeResponse = CreateScopeResponse.builder();

        createScopeResponse.id( scope.getId() );
        createScopeResponse.scopeName( scope.getName() );
        createScopeResponse.description( scope.getDescription() );
        createScopeResponse.accessKey( scope.getAccessKey() );

        return createScopeResponse.build();
    }

    private String scopeCreatedByEmail(Scope scope) {
        if ( scope == null ) {
            return null;
        }
        User createdBy = scope.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        String email = createdBy.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
