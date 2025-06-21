
package com.driagon.services.connector.processors;

import com.driagon.services.connector.clients.ConfigPropertiesClient;
import com.driagon.services.connector.models.responses.PropertyResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigClientEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String LOG_PREFIX = "[CONFIG-DRIVER-EARLY] ";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String url = environment.getProperty("config.url");
        String scope = environment.getProperty("config.scope");
        String accessKey = environment.getProperty("config.key");

        if (scope == null || accessKey == null || url == null) {
            return;
        }

        try {
            System.out.println(LOG_PREFIX + "Cargando propiedades iniciales...");
            Set<PropertyResponse> properties = new ConfigPropertiesClient().fetchProperties(url, scope, accessKey);

            Map<String, Object> props = new HashMap<>();
            for (PropertyResponse prop : properties) {
                props.put(prop.getKey(), prop.getValue());
            }

            MapPropertySource propertySource = new MapPropertySource("config-server-properties", props);
            environment.getPropertySources().addFirst(propertySource);

            System.out.printf("%sProperties iniciales cargadas: %d%n", LOG_PREFIX, props.size());

        } catch (Exception e) {
            System.err.printf("%sError en carga inicial: %s%n", LOG_PREFIX, e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}