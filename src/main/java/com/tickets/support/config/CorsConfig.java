package com.tickets.support.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * La configuration CORS est maintenant gérée de manière centralisée dans WebSecurityConfig.
     * Ce fichier est désactivé pour éviter les conflits. Il est recommandé de le supprimer
     * ainsi que WebConfig.java.
     */
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    // }
}