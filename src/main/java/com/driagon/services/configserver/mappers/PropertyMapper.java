package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.requests.SetPropertiesRequest;
import com.driagon.services.configserver.entities.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    @Mappings({
        @Mapping(target = "key", source = "key"),
        @Mapping(target = "value", source = "value"),
        @Mapping(target = "secret", source = "secret")
    })
    Set<Property> mapPropertyRequestToPropertyEntity(Set<SetPropertiesRequest> request);
}