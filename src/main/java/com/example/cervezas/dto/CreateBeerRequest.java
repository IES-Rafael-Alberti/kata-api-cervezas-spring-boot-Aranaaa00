package com.example.cervezas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Schema(description = "Petición para crear una nueva cerveza con todos los campos requeridos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBeerRequest {

    @Schema(description = "Identificador de la cervecería", example = "5")
    @NotNull(message = "El ID de la cervecería es obligatorio")
    private Long cerveceriaId;

    @Schema(description = "Nombre de la cerveza", example = "IPA Artesanal")
    @NotBlank(message = "El nombre de la cerveza no puede estar vacío")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    private String nombre;

    @Schema(description = "Identificador de la categoría", example = "3")
    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoriaId;

    @Schema(description = "Identificador del estilo", example = "7")
    @NotNull(message = "El ID del estilo es obligatorio")
    private Long estiloId;

    @Schema(description = "Graduación alcohólica (ABV)", example = "6.5")
    @NotNull(message = "La graduación alcohólica es obligatoria")
    private Float graduacionAlcoholica;

    @Schema(description = "International Bitterness Units (IBU)", example = "45.0")
    @NotNull(message = "El IBU es obligatorio")
    private Float ibu;

    @Schema(description = "Standard Reference Method (SRM) - Color", example = "12.0")
    @NotNull(message = "El SRM es obligatorio")
    private Float srm;

    @Schema(description = "Código Universal de Producto", example = "123456")
    @NotNull(message = "El UPC es obligatorio")
    private Integer upc;

    @Schema(description = "Ruta del archivo de imagen", example = "/images/beer1.jpg")
    @NotBlank(message = "La ruta de archivo es obligatoria")
    private String rutaArchivo;

    @Schema(description = "Descripción de la cerveza", example = "Una deliciosa IPA con notas cítricas")
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Schema(description = "ID del usuario que agrega la cerveza", example = "1")
    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer usuarioAgregador;
}
