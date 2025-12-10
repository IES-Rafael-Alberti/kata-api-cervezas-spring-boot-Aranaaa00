package com.example.cervezas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Schema(description = "Petición para actualizar una cerveza existente. Todos los campos son opcionales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBeerRequest {

    @Schema(description = "Identificador de la cervecería", example = "5")
    private Long cerveceriaId;

    @Schema(description = "Nombre de la cerveza", example = "IPA Artesanal")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
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

    @Schema(description = "ID del usuario que modifica la cerveza", example = "1")
    private Integer usuarioAgregador;
}
