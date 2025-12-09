package com.example.cervezas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "beers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    // ID: Autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID de cervecería - NOT NULL
    @Column(name = "brewery_id", nullable = false)
    private Long cerveceriaId;

    // Nombre - NOT NULL
    @Column(name = "name", nullable = false)
    private String nombre;

    // ID de categoría - NOT NULL
    @Column(name = "cat_id", nullable = false)
    private Long categoriaId;

    // ID de estilo - NOT NULL
    @Column(name = "style_id", nullable = false)
    private Long estiloId;

    // ABV (Alcohol by Volume) - NOT NULL
    @Column(name = "abv", nullable = false)
    private Float graduacionAlcoholica;

    // IBU (Unidades de amargor) - NOT NULL
    @Column(name = "ibu", nullable = false)
    private Float ibu;

    // SRM (Color) - NOT NULL
    @Column(name = "srm", nullable = false)
    private Float srm;

    // UPC (Código de barras) - NOT NULL
    @Column(name = "upc", nullable = false)
    private Integer upc;

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
