package com.cocktail;

import com.cocktail.property.FileUploadProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableConfigurationProperties({FileUploadProperties.class})

public class WebCurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebCurationApplication.class, args);
    }

    @Bean public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


     
    
}
