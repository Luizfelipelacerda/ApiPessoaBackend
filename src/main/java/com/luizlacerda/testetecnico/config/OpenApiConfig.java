package com.luizlacerda.testetecnico.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(

                        name = "Luiz Felipe Chaves de Lacerda",
                        email = "luizfelipe.c.lacerda@gmail.com",
                        url = "https://github.com/Luizfelipelacerda"
                ),
                title = "Pessoa API",
                version = "1.0",
                description = "Api de Pessoas"

        ),
        servers = {
                @Server(
                        description = "local Dev",
                        url = "http://localhost:8080"
                )
        }

)
public class OpenApiConfig {
}
