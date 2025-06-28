package com.musanlori.dev.crud.api.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CRUD API",
                version = "0.0.1-snapshot",
                description = """
                        Simple API/REST microservice that shows the typical operations of CRUD Application\s
                        in an articles inventory mounted in a DB server and implements the Oauth2 standard\s
                        to secure endpoints.
                        """,
                contact = @Contact(
                        name = "RSantiagoDeveloper",
                        url = "https://github.com/RSantiagodeveloper"
                )
        )
)
public class OpenAPiConfig {
}
