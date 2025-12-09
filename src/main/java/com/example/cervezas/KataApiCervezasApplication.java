package com.example.cervezas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot
 * 
 * Esta clase inicia la aplicación REST API para la gestión de cervezas.
 * Detecta automáticamente componentes en el paquete y subpaquetes.
 */
@SpringBootApplication
public class KataApiCervezasApplication {

    public static void main(String[] args) {
        SpringApplication.run(KataApiCervezasApplication.class, args);
    }

}
