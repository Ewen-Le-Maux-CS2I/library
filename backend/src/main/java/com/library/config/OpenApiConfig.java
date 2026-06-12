package com.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI libraryOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Library Backend API")
                .description("API de gestion de bibliothèque (ouvrages, exemplaires, emprunts)")
                .version("v0.0.1"));
  }
}
