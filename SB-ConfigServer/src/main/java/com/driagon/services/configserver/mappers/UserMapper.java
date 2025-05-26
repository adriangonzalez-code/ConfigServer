package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.requests.UserRequest;
import com.driagon.services.configserver.dto.responses.UserResponse;
import com.driagon.services.configserver.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "active", constant = "true"),
        @Mapping(target = "firstName", source = "firstName"),
        @Mapping(target = "lastName", source = "lastName"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "password", source = "password"),
        @Mapping(target = "role.id", source = "roleId")
    })
    User mapRequestToEntity(UserRequest request);

    @Mappings({
        @Mapping(target = "firstName", source = "firstName"),
        @Mapping(target = "lastName", source = "lastName"),
        @Mapping(target = "email", source = "email")
    })
    UserResponse mapEntityToResponse(User user);
}