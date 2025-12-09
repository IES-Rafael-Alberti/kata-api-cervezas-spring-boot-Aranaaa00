package com.example.cervezas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "breweries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brewery {

    // ID: Autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre - NOT NULL
    @Column(name = "name", nullable = false)
    private String nombre;

    // Dirección 1 - NOT NULL
    @Column(name = "address1", nullable = false)
    private String direccion1;

    // Dirección 2 - NOT NULL
    @Column(name = "address2", nullable = false)
    private String direccion2;

    // Ciudad - NOT NULL
    @Column(name = "city", nullable = false)
    private String ciudad;

    // Estado - NOT NULL
    @Column(name = "state", nullable = false)
    private String estado;

    // Código postal - NOT NULL
    @Column(name = "code", nullable = false, length = 25)
    private String codigoPostal;

    // País - NOT NULL
    @Column(name = "country", nullable = false)
    private String pais;

    // Teléfono - NOT NULL
    @Column(name = "phone", nullable = false, length = 50)
    private String telefono;

    // Sitio web - NOT NULL
    @Column(name = "website", nullable = false)
    private String sitioWeb;

    // Ruta de archivo - NOT NULL
    @Column(name = "filepath", nullable = false)
    private String rutaArchivo;

    // Descripción - NOT NULL
    @Column(name = "descript", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    // Usuario que agregó - NOT NULL
    @Column(name = "add_user", nullable = false)
    private Integer usuarioAgregador;

    // Última modificación - NOT NULL
    @Column(name = "last_mod", nullable = false)
    private LocalDateTime ultimaModificacion;
}
