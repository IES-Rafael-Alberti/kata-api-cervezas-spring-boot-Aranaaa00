package com.example.cervezas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Respuesta con los datos completos de una cerveza")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerResponse {

    @Schema(description = "Identificador único de la cerveza", example = "1")
    private Long id;
    
    @Schema(description = "Identificador de la cervecería", example = "5")
    private Long cerveceriaId;
    
    @Schema(description = "Nombre de la cerveza", example = "IPA Artesanal")
    private String nombre;
    
    @Schema(description = "Identificador de la categoría", example = "3")
    private Long categoriaId;
    
    @Schema(description = "Identificador del estilo", example = "7")
    private Long estiloId;
    
    @Schema(description = "Graduación alcohólica (ABV)", example = "6.5")
    private Float graduacionAlcoholica;
    
    @Schema(description = "International Bitterness Units (IBU)", example = "45.0")
    private Float ibu;
    
    @Schema(description = "Standard Reference Method (SRM) - Color", example = "12.0")
    private Float srm;
    
    @Schema(description = "Código Universal de Producto", example = "123456")
    private Integer upc;
    
    @Schema(description = "Ruta del archivo de imagen", example = "/images/beer1.jpg")
    private String rutaArchivo;
    
    @Schema(description = "Descripción de la cerveza", example = "Una deliciosa IPA con notas cítricas")
    private String descripcion;
    
    @Schema(description = "ID del usuario que agregó la cerveza", example = "1")
    private Integer usuarioAgregador;
    
    @Schema(description = "Fecha de última modificación", example = "2025-12-09T10:30:00")
    private LocalDateTime ultimaModificacion;
}
