package com.driagon.services.configserver.services;

import com.driagon.services.configserver.dto.requests.SetPropertiesRequest;
import com.driagon.services.configserver.dto.responses.SetPropertyResponse;

import java.util.Set;

public interface IPropertyService {

    Set<SetPropertyResponse> setProperties(String scopeRequest, Set<SetPropertiesRequest> request);
}