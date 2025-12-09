package com.example.cervezas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configuracionOpenAPI() {
        Server servidorLocal = new Server();
        servidorLocal.setUrl("http://localhost:8080");
        servidorLocal.setDescription("Servidor de desarrollo local");

        Contact contacto = new Contact();
        contacto.setName("Equipo de desarrollo");
        contacto.setEmail("desarrollo@cervezas.com");

        License licencia = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info informacion = new Info()
                .title("API REST de Cervezas")
                .version("1.0.0")
                .description("API para gestión completa de cervezas, cervecerías, categorías y estilos")
                .contact(contacto)
                .license(licencia);

        return new OpenAPI()
                .info(informacion)
                .servers(List.of(servidorLocal));
    }
}
