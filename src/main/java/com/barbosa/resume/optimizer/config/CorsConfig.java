package com.barbosa.resume.optimizer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to enable Cross-Origin Resource Sharing (CORS).
 * This allows frontend applications (running on different ports) to access the API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:3000", "http://localhost:5173", "*") // Allow React/Vite/All
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(false); // Set to true if you need cookies/auth
    }
}
