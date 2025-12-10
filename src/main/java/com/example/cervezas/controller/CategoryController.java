package com.example.cervezas.controller;

import com.example.cervezas.model.Category;
import com.example.cervezas.service.CategoryService;
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

@Tag(name = "Categorías", description = "Operaciones de consulta para categorías")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Obtener todas las categorías", description = "Obtiene un listado paginado de todas las categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/categories")
    public ResponseEntity<Page<Category>> obtenerTodas(
            @Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Category> categorias = categoryService.obtenerTodas(pageable);
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Obtener categoría por ID", description = "Obtiene los detalles de una categoría específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/categorie/{id}")
    public ResponseEntity<Category> obtenerPorId(
            @Parameter(description = "ID de la categoría") @PathVariable Long id) {
        Category categoria = categoryService.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @Operation(summary = "Obtener metadatos de paginación", description = "Retorna solo las cabeceras con información de paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Metadatos obtenidos exitosamente")
    })
    @RequestMapping(value = "/categories", method = RequestMethod.HEAD)
    public ResponseEntity<Void> obtenerCabeceras(
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Elementos por página") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoriesPage = categoryService.obtenerTodas(pageable);
        
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(categoriesPage.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(categoriesPage.getTotalPages()))
                .header("X-Current-Page", String.valueOf(categoriesPage.getNumber()))
                .header("X-Page-Size", String.valueOf(categoriesPage.getSize()))
                .build();
    }
}
