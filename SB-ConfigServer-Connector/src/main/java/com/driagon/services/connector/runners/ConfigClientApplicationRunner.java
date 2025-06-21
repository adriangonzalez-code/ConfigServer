package com.driagon.services.connector.runners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigClientApplicationRunner implements ApplicationRunner, Ordered {

    private final ConfigurableEnvironment environment;

    public ConfigClientApplicationRunner(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("🚀 [config-driver] Config Client iniciado correctamente");

        boolean hasConfigProperties = environment.getPropertySources()
                .stream()
                .anyMatch(ps -> ps.getName().equals("config-server-properties"));

        if (hasConfigProperties) {
            log.info("✅ [config-driver] Propiedades del config-server detectadas y disponibles");

            String url = environment.getProperty("config.url");
            String scope = environment.getProperty("config.scope");
            log.info("📡 [config-driver] Configuración activa - URL: {}, Scope: {}", url, scope);

        } else {
            log.warn("⚠️ [config-driver] No se detectaron propiedades del config-server");
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}