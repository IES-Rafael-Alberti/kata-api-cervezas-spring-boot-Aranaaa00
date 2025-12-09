package com.example.cervezas.controller;

import com.example.cervezas.model.Brewery;
import com.example.cervezas.service.BreweryService;
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

@Tag(name = "Cervecerías", description = "Operaciones de consulta para cervecerías")
@RestController
@RequiredArgsConstructor
public class BreweryController {

    private final BreweryService breweryService;

    @Operation(summary = "Obtener todas las cervecerías", description = "Obtiene un listado paginado de todas las cervecerías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/breweries")
    public ResponseEntity<Page<Brewery>> obtenerTodas(
            @Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Brewery> cervecerias = breweryService.obtenerTodas(pageable);
        return ResponseEntity.ok(cervecerias);
    }

    @Operation(summary = "Obtener cervecería por ID", description = "Obtiene los detalles de una cervecería específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cervecería encontrada"),
            @ApiResponse(responseCode = "404", description = "Cervecería no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/brewerie/{id}")
    public ResponseEntity<Brewery> obtenerPorId(
            @Parameter(description = "ID de la cervecería") @PathVariable Long id) {
        Brewery cerveceria = breweryService.obtenerPorId(id);
        return ResponseEntity.ok(cerveceria);
    }

    @Operation(summary = "Obtener metadatos de paginación", description = "Retorna solo las cabeceras con información de paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Metadatos obtenidos exitosamente")
    })
    @RequestMapping(value = "/breweries", method = RequestMethod.HEAD)
    public ResponseEntity<Void> obtenerCabeceras(
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Elementos por página") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Brewery> breweriesPage = breweryService.obtenerTodas(pageable);
        
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(breweriesPage.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(breweriesPage.getTotalPages()))
                .header("X-Current-Page", String.valueOf(breweriesPage.getNumber()))
                .header("X-Page-Size", String.valueOf(breweriesPage.getSize()))
                .build();
    }
}
