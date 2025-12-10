package com.example.cervezas.controller;

import com.example.cervezas.model.Style;
import com.example.cervezas.service.StyleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Estilos", description = "Operaciones de consulta para estilos de cerveza")
@RestController
@RequiredArgsConstructor
public class StyleController {

    private final StyleService styleService;

    @Operation(summary = "Obtener todos los estilos", description = "Obtiene un listado paginado de todos los estilos de cerveza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/styles")
    public ResponseEntity<Page<Style>> obtenerTodos(
            @Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Style> estilos = styleService.obtenerTodos(pageable);
        return ResponseEntity.ok(estilos);
    }

    @Operation(summary = "Obtener estilo por ID", description = "Obtiene los detalles de un estilo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estilo encontrado"),
            @ApiResponse(responseCode = "404", description = "Estilo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/style/{id}")
    public ResponseEntity<Style> obtenerPorId(
            @Parameter(description = "ID del estilo") @PathVariable Long id) {
        Style estilo = styleService.obtenerPorId(id);
        return ResponseEntity.ok(estilo);
    }

    @Operation(summary = "Obtener metadatos de paginación", description = "Retorna solo las cabeceras con información de paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Metadatos obtenidos exitosamente")
    })
    @RequestMapping(value = "/styles", method = RequestMethod.HEAD)
    public ResponseEntity<Void> obtenerCabeceras(
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Elementos por página") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Style> stylesPage = styleService.obtenerTodos(pageable);
        
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(stylesPage.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(stylesPage.getTotalPages()))
                .header("X-Current-Page", String.valueOf(stylesPage.getNumber()))
                .header("X-Page-Size", String.valueOf(stylesPage.getSize()))
                .build();
    }
}
