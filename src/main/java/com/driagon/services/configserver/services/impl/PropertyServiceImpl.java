package com.driagon.services.configserver.services.impl;

import com.driagon.services.configserver.dto.requests.SetPropertiesRequest;
import com.driagon.services.configserver.dto.responses.SetPropertyResponse;
import com.driagon.services.configserver.entities.Property;
import com.driagon.services.configserver.mappers.PropertyMapper;
import com.driagon.services.configserver.repositories.IPropertyRepository;
import com.driagon.services.configserver.services.IPropertyService;
import com.driagon.services.error.exceptions.NotFoundException;
import com.driagon.services.error.exceptions.ProcessException;
import com.driagon.services.logging.annotations.ExceptionLog;
import com.driagon.services.logging.annotations.Loggable;
import com.driagon.services.logging.utils.MaskedLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements IPropertyService {

    private final IPropertyRepository propertyRepository;

    private final PropertyMapper mapper;

    private final static MaskedLogger log = MaskedLogger.getLogger(PropertyServiceImpl.class);

    @Override
    @Loggable(message = "Setting properties for scope: {0}", exceptions = {
            @ExceptionLog(value = NotFoundException.class, message = "Scope not found: {0}"),
            @ExceptionLog(value = ProcessException.class, message = "Error processing properties for scope: {0}")
    })
    @Transactional
    public Set<SetPropertyResponse> setProperties(String scopeRequest, Set<SetPropertiesRequest> request) {
        try {
            Set<Property> properties = this.propertyRepository.findByScope_NameIgnoreCaseAndKeyNotInIgnoreCase(scopeRequest,
                    request.stream()
                            .map(SetPropertiesRequest::getKey)
                            .collect(Collectors.toSet()));

            if (properties.isEmpty()) {
                log.warn("No properties found for scope: {}", scopeRequest);
                throw new NotFoundException("Scope not found: " + scopeRequest);
            } else {
                log.info("Found {} properties for scope {} to be deleted", properties.size(), scopeRequest);
                this.propertyRepository.deleteAll(properties);

                properties = this.mapper.mapPropertyRequestToPropertyEntity(request);
                log.info("Found {} properties for scope {} to be added/updated", properties.size(), scopeRequest);


                return this.propertyRepository.saveAll(properties)
                        .stream()
                        .map(property ->
                                SetPropertyResponse.builder()
                                        .key(property.getKey())
                                        .value(property.getValue())
                                        .secret(property.isSecret())
                                        .build()
                        ).collect(Collectors.toSet());
            }
        } catch (DataAccessException ex) {
            throw new ProcessException(ex.getMessage());
        }
    }
}