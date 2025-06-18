package com.driagon.services.connector.clients;

import com.driagon.services.connector.models.responses.PropertyResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConfigPropertiesClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<PropertyResponse> fetchProperties(String url, String scope, String accessKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CONFIG-SCOPE", scope);
        headers.set("X-CONFIG-KEY", accessKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<PropertyResponse[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                PropertyResponse[].class
        );

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}