package com.example.cervezas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

    @Value("${file.upload.dir:uploads/images}")
    private String directorioSubida;

    @Bean
    public Path directorioAlmacenamiento() throws IOException {
        Path ruta = Paths.get(directorioSubida).toAbsolutePath().normalize();
        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }
        return ruta;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + directorioSubida + "/");
    }
}
