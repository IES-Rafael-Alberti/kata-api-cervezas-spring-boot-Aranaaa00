package com.example.cervezas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    // ID: Autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de categoría - NOT NULL
    @Column(name = "cat_name", nullable = false)
    private String nombreCategoria;

    // Última modificación - NOT NULL
    @Column(name = "last_mod", nullable = false)
    private LocalDateTime ultimaModificacion;
}
