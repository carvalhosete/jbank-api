package com.jbank.jbank.adapters.in.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI jbankOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                .title("JBank API")
                .description("API RESTful para gestão de contas e transferências, construída com Arq. Hexagonal e Mensageria.")
                .version("v1.0.0"));
    }
}
