package com.driagon.services.connector.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigClientEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();

        log.info("🎉 [config-driver] Aplicación completamente lista - verificando configuración");

        String url = environment.getProperty("config.url");
        String scope = environment.getProperty("config.scope");

        if (url != null && scope != null) {
            log.info("✅ [config-driver] Config-server configurado: {}/{}", url, scope);
        } else {
            log.warn("⚠️ [config-driver] Config-server no configurado");
        }
    }
}