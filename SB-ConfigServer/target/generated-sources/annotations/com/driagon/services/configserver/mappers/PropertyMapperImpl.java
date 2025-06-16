package com.driagon.services.configserver.mappers;

import com.driagon.services.configserver.dto.requests.SetPropertyRequest;
import com.driagon.services.configserver.dto.responses.SetPropertyResponse;
import com.driagon.services.configserver.entities.Property;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-16T12:34:18-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class PropertyMapperImpl extends PropertyMapper {

    @Override
    public Property mapRequestToProperty(SetPropertyRequest request) {
        if ( request == null ) {
            return null;
        }

        Property.PropertyBuilder property = Property.builder();

        property.id( request.getId() );
        property.key( request.getKey() );
        property.secret( request.isSecret() );

        property.value( processValue(request.getValue(), request.isSecret()) );

        return property.build();
    }

    @Override
    public SetPropertyResponse mapPropertyEntityToSetPropertyResponse(Property properties) {
        if ( properties == null ) {
            return null;
        }

        SetPropertyResponse.SetPropertyResponseBuilder setPropertyResponse = SetPropertyResponse.builder();

        setPropertyResponse.id( properties.getId() );
        setPropertyResponse.key( properties.getKey() );
        setPropertyResponse.secret( properties.isSecret() );

        setPropertyResponse.value( processValueForResponse(properties.getValue(), properties.isSecret()) );

        return setPropertyResponse.build();
    }
}
