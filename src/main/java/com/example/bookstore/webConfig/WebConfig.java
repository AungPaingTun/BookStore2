package com.example.bookstore.webConfig;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") //Allow all endpoints
                .allowedOriginPatterns("*")// Specify allowed origins (image host)
                .allowedMethods("GET", "POST","PUT","DELETE","OPTIONS")//ALLOW HTTP METHODS
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); //Allow credentials if needed

    }
}
