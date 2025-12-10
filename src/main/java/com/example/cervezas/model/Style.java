package com.example.cervezas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "styles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Style {

    // ID: Autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID de categoría - NOT NULL
    @Column(name = "cat_id", nullable = false)
    private Long categoriaId;

    // Nombre del estilo - NOT NULL
    @Column(name = "style_name", nullable = false)
    private String nombreEstilo;

    // Última modificación - NOT NULL
    @Column(name = "last_mod", nullable = false)
    private LocalDateTime ultimaModificacion;
}
