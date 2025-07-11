package com.brendanbuchanan.pt_website_backend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                String allowedOrigin = System.getenv("FRONTEND_ORIGIN");
                registry.addMapping("/**")  // allow all routes
                        .allowedOrigins(allowedOrigin != null ? allowedOrigin : "http://localhost:3000") // your frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowCredentials(true);
            }
        };
    }
}
