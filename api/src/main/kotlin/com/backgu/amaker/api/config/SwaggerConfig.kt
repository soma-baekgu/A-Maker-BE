package com.backgu.amaker.api.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class SwaggerConfig {
    @Bean
    fun customOpenAPI(
        @Value("\${swagger.url}") url: String,
        @Value("\${swagger.locate}") locate: String,
    ): OpenAPI {
        val info =
            Info()
                .title("Backgu AMaker API")
                .description("Api Documentation")

        return OpenAPI()
            .addServersItem(Server().url(url).description("$locate Server"))
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .components(
                Components().addSecuritySchemes(
                    "bearerAuth",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .`in`(SecurityScheme.In.HEADER)
                        .name(HttpHeaders.AUTHORIZATION),
                ),
            ).info(info)
    }
}
