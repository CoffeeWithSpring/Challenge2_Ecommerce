package com.compassuol.sp.challenge.ecommerce.domain.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rest API - Ecommerce")
                                .description("API para gestão de um Ecommerce")
                                .version("v1")
                                .contact(new Contact().name("CoffeeWithSpring"))
                );
    }
}
