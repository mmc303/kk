package com.example.demo.repository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configurar el CORS para todos los endpoints bajo /api/**
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200")  // Origen permitido (Angular)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Métodos permitidos
                .allowedHeaders("*")  // Permitir todos los headers
                .allowCredentials(true);  // Permitir credenciales (por ejemplo, cookies, headers de autorización)
    }
}
