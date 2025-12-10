package com.example.cervezas.controller;

import com.example.cervezas.exception.RecursoNoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;

@Tag(name = "Im\u00e1genes", description = "Operaciones para servir im\u00e1genes de cervezas")
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final Path directorioAlmacenamiento;

    @Operation(summary = "Obtener imagen", description = "Obtiene una imagen de cerveza por su nombre de archivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen encontrada"),
            @ApiResponse(responseCode = "400", description = "Nombre de archivo inválido"),
            @ApiResponse(responseCode = "404", description = "Imagen no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{nombreArchivo:.+}")
    public ResponseEntity<Resource> obtenerImagen(
            @Parameter(description = "Nombre del archivo de imagen") @PathVariable String nombreArchivo) {
        log.info("Solicitando imagen: {}", nombreArchivo);
        
        Resource recurso = cargarRecurso(nombreArchivo);
        validarRecursoDisponible(recurso, nombreArchivo);
        
        String tipoContenido = determinarTipoContenido(nombreArchivo);
        ResponseEntity<Resource> respuesta = ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(tipoContenido))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
        
        return respuesta;
    }

    private Resource cargarRecurso(String nombreArchivo) {
        try {
            Path rutaArchivo = directorioAlmacenamiento.resolve(nombreArchivo).normalize();
            return new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            log.error("Error al construir URL para el archivo: {}", nombreArchivo, e);
            throw new IllegalArgumentException("Nombre de archivo inválido: " + nombreArchivo, e);
        }
    }

    private void validarRecursoDisponible(Resource recurso, String nombreArchivo) {
        if (!recurso.exists() || !recurso.isReadable()) {
            log.warn("Imagen no encontrada o no legible: {}", nombreArchivo);
            throw new RecursoNoEncontradoException("Imagen", nombreArchivo);
        }
    }

    private String determinarTipoContenido(String nombreArchivo) {
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }
}
