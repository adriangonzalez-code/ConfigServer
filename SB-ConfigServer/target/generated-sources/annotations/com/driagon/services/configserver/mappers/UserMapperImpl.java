package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.requests.UpdateUserRequest;
import com.driagon.services.configserver.dto.requests.UserRequest;
import com.driagon.services.configserver.dto.responses.UserResponse;
import com.driagon.services.configserver.entities.Role;
import com.driagon.services.configserver.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-16T12:34:18-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapUserRequestToUserEntity(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.role( userRequestToRole( request ) );
        user.firstName( request.getFirstName() );
        user.lastName( request.getLastName() );
        user.email( request.getEmail() );
        user.password( request.getPassword() );

        user.active( true );

        return user.build();
    }

    @Override
    public UserResponse mapUserEntityToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.firstName( user.getFirstName() );
        userResponse.lastName( user.getLastName() );
        userResponse.email( user.getEmail() );
        userResponse.roleName( userRoleName( user ) );

        return userResponse.build();
    }

    @Override
    public void mapUpdateUserRequestToUserEntity(UpdateUserRequest request, User user) {
        if ( request == null ) {
            return;
        }

        user.setId( request.getId() );
        user.setFirstName( request.getFirstName() );
        user.setLastName( request.getLastName() );
        user.setEmail( request.getEmail() );
        user.setActive( request.isActive() );
    }

    protected Role userRequestToRole(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.id( userRequest.getRoleId() );

        return role.build();
    }

    private String userRoleName(User user) {
        if ( user == null ) {
            return null;
        }
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        String name = role.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
