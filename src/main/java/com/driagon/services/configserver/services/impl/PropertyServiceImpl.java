package com.driagon.services.configserver.services.impl;

import com.driagon.services.configserver.dto.requests.SetPropertyRequest;
import com.driagon.services.configserver.dto.responses.SetPropertyResponse;
import com.driagon.services.configserver.entities.Property;
import com.driagon.services.configserver.entities.User;
import com.driagon.services.configserver.mappers.PropertyMapper;
import com.driagon.services.configserver.repositories.IPropertyRepository;
import com.driagon.services.configserver.repositories.IScopeRepository;
import com.driagon.services.configserver.repositories.IUserRepository;
import com.driagon.services.configserver.services.IPropertyService;
import com.driagon.services.error.exceptions.NotFoundException;
import com.driagon.services.error.exceptions.ProcessException;
import com.driagon.services.logging.annotations.ExceptionLog;
import com.driagon.services.logging.annotations.Loggable;
import com.driagon.services.logging.utils.MaskedLogger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements IPropertyService {

    private final IPropertyRepository propertyRepository;

    private final PropertyMapper mapper;

    private final IScopeRepository scopeRepository;

    private final IUserRepository userRepository;

    private final static MaskedLogger log = MaskedLogger.getLogger(PropertyServiceImpl.class);

    @Override
    @Loggable(message = "Setting properties for scope: {0}", exceptions = {
            @ExceptionLog(value = NotFoundException.class, message = "Scope not found: {0}"),
            @ExceptionLog(value = ProcessException.class, message = "Error processing properties for scope: {0}")
    })
    @Transactional
    public Set<SetPropertyResponse> setProperties(Long scopeRequest, Set<SetPropertyRequest> request) {
        try {
            if (!this.scopeRepository.existsById(scopeRequest)) {
                throw new NotFoundException("Scope not found: " + scopeRequest);
            }

            User user = this.userRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new NotFoundException("No user found."));

            Set<Property> existingProperties = this.propertyRepository.findByScope_Id(scopeRequest);

            Set<Long> requestPropertyIds = new HashSet<>();
            Set<String> requestPropertyKeys = new HashSet<>();
            Set<String> requestKeysWithoutId = new HashSet<>();

            for (SetPropertyRequest req : request) {
                if (req.getId() != null) {
                    requestPropertyIds.add(req.getId());
                } else {
                    requestKeysWithoutId.add(req.getKey());
                }
                requestPropertyKeys.add(req.getKey());
            }

            Set<Property> propsToBeDeleted = existingProperties.stream()
                    .filter(property -> !requestPropertyIds.contains(property.getId()) &&
                            !requestPropertyKeys.contains(property.getKey()))
                    .collect(Collectors.toSet());

            log.info("Found {} properties for scope {} to be deleted", propsToBeDeleted.size(), scopeRequest);

            if (CollectionUtils.isNotEmpty(propsToBeDeleted)) {
                this.propertyRepository.deleteAll(propsToBeDeleted);
            }

            Map<String, Property> existingPropsByKey;

            log.debug("Using batch query for {} keys without ID", requestKeysWithoutId.size());
            List<Property> existingPropsForKeys = this.propertyRepository
                    .findByScopeIdAndKeyIn(scopeRequest, requestKeysWithoutId);

            existingPropsByKey = existingPropsForKeys.stream()
                    .collect(Collectors.toMap(Property::getKey, Function.identity()));

            Set<Property> propertiesToSave = new HashSet<>();

            for (SetPropertyRequest propertyRequest : request) {
                Property propertyToSave;

                if (propertyRequest.getId() != null) {
                    propertyToSave = this.mapper.mapRequestToProperty(propertyRequest);
                    log.debug("Updating property with provided ID: {} and key: {}",
                            propertyRequest.getId(), propertyRequest.getKey());

                } else {
                    Property existingProperty = existingPropsByKey.get(propertyRequest.getKey());

                    if (existingProperty != null) {
                        propertyToSave = this.mapper.mapRequestToProperty(propertyRequest);
                        propertyToSave.setId(existingProperty.getId());
                        log.debug("Updating property with existing key: {} using ID: {}",
                                propertyRequest.getKey(), existingProperty.getId());
                    } else {
                        propertyToSave = this.mapper.mapRequestToProperty(propertyRequest);
                        log.debug("Creating new property with key: {}", propertyRequest.getKey());
                    }
                }

                propertyToSave.setScope(this.scopeRepository.getReferenceById(scopeRequest));
                propertyToSave.setCreatedBy(user);

                propertiesToSave.add(propertyToSave);
            }

            Set<Property> savedProperties = new HashSet<>(this.propertyRepository.saveAll(propertiesToSave));

            return savedProperties.stream().map(this.mapper::mapPropertyEntityToSetPropertyResponse).collect(Collectors.toSet());

        } catch (DataAccessException ex) {
            throw new ProcessException(ex.getMessage());
        }
    }
}