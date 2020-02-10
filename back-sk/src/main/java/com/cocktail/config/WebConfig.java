package com.cocktail.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping( "/**" )
                .allowedOrigins( "http://localhost:3000" )
                .allowedMethods( "GET", "POST", "DELETE", "OPTIONS" )
                .allowedHeaders( "*" )
                .allowCredentials( true )
                .exposedHeaders( "authorization" )
                .maxAge( 3600 );
    }
}