package com.driagon.services.connector.processors;

import com.driagon.services.connector.clients.ConfigPropertiesClient;
import com.driagon.services.connector.models.responses.PropertyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigClientEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(ConfigClientEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String url = environment.getProperty("config.url");
        String scope = environment.getProperty("config.scope");
        String accessKey = environment.getProperty("config.key");

        if (scope == null || accessKey == null) {
            log.info("[config-driver] No se ha configurado el scope o accessKey, no se cargarán las propiedades del config-server.");
            return;
        }

        try {
            log.info("[config-driver] Cargando properties desde el config-server: url={}, scope={}", url, scope);
            List<PropertyResponse> properties = new ConfigPropertiesClient().fetchProperties(url, scope, accessKey);

            Map<String, Object> props = new HashMap<>();
            for (PropertyResponse prop : properties) {
                String value = prop.getValue();
                props.put(prop.getKey(), value);
            }

            MapPropertySource propertySource = new MapPropertySource("config-server-properties", props);
            environment.getPropertySources().addFirst(propertySource);

            log.info("[config-driver] Properties obtenidas del config-server: {}", props);

        } catch (Exception e) {
            log.error("[config-driver] Error al cargar las propiedades desde el config-server: {}", e.getMessage(), e);
        }
    }
}