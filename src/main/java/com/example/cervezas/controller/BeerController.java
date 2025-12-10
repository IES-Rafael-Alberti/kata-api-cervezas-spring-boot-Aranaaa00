package com.example.cervezas.controller;

import com.example.cervezas.dto.CreateBeerRequest;
import com.example.cervezas.dto.UpdateBeerRequest;
import com.example.cervezas.dto.BeerResponse;
import com.example.cervezas.service.BeerService;
import com.example.cervezas.service.FileStorageService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

@Tag(name = "Cervezas", description = "Operaciones CRUD para gestión de cervezas")
@RestController
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "Crear nueva cerveza", description = "Crea una nueva cerveza en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cerveza creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/beer")
    public ResponseEntity<BeerResponse> crear(@Valid @RequestBody CreateBeerRequest peticion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(beerService.createBeer(peticion));
    }

    @Operation(summary = "Obtener todas las cervezas", description = "Obtiene un listado paginado de todas las cervezas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/beers")
    public ResponseEntity<Page<BeerResponse>> obtenerTodas(
            @Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(beerService.getAllBeers(pageable));
    }

    @Operation(summary = "Obtener cerveza por ID", description = "Obtiene los detalles de una cerveza específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerveza encontrada"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/beer/{id}")
    public ResponseEntity<BeerResponse> obtenerPorId(
            @Parameter(description = "ID de la cerveza") @PathVariable Long id) {
        BeerResponse cerveza = beerService.getBeerById(id);
        return ResponseEntity.ok(cerveza);
    }

    @Operation(summary = "Buscar cervezas por nombre", description = "Busca cervezas que contengan el texto especificado en su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/beer/buscar")
    public ResponseEntity<Page<BeerResponse>> buscarPorNombre(
            @Parameter(description = "Texto a buscar en el nombre") @RequestParam String nombre,
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Elementos por página") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BeerResponse> resultados = beerService.searchBeersByName(nombre, pageable);
        return ResponseEntity.ok(resultados);
    }

    @Operation(summary = "Filtrar cervezas por estilo", description = "Obtiene todas las cervezas de un estilo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtrado exitoso"),
            @ApiResponse(responseCode = "404", description = "Estilo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/beer/filtrar/estilo/{estiloId}")
    public ResponseEntity<Page<BeerResponse>> obtenerPorEstilo(
            @Parameter(description = "ID del estilo") @PathVariable Long estiloId,
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Elementos por página") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BeerResponse> resultados = beerService.getBeersByStyle(estiloId, pageable);
        return ResponseEntity.ok(resultados);
    }

    @Operation(summary = "Filtrar cervezas por cervecería", description = "Obtiene todas las cervezas de una cervecería específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtrado exitoso"),
            @ApiResponse(responseCode = "404", description = "Cervecería no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/beer/filtrar/cerveceria/{cerveceriaId}")
    public ResponseEntity<Page<BeerResponse>> obtenerPorCerveceria(
            @Parameter(description = "ID de la cervecería") @PathVariable Long cerveceriaId,
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Elementos por página") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BeerResponse> resultados = beerService.getBeersByBrewery(cerveceriaId, pageable);
        return ResponseEntity.ok(resultados);
    }

    @Operation(summary = "Actualizar cerveza completa", description = "Actualiza todos los campos de una cerveza existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerveza actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/beer/{id}")
    public ResponseEntity<BeerResponse> actualizar(
            @Parameter(description = "ID de la cerveza") @PathVariable Long id, 
            @Valid @RequestBody UpdateBeerRequest peticion) {
        BeerResponse cervezaActualizada = beerService.updateBeer(id, peticion);
        return ResponseEntity.ok(cervezaActualizada);
    }

    @Operation(summary = "Actualizar cerveza parcialmente", description = "Actualiza solo los campos especificados de una cerveza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerveza actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/beer/{id}")
    public ResponseEntity<BeerResponse> actualizarParcial(
            @Parameter(description = "ID de la cerveza") @PathVariable Long id, 
            @RequestBody UpdateBeerRequest peticion) {
        BeerResponse cervezaActualizada = beerService.updateBeer(id, peticion);
        return ResponseEntity.ok(cervezaActualizada);
    }

    @Operation(summary = "Eliminar cerveza", description = "Elimina una cerveza del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cerveza eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/beer/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la cerveza") @PathVariable Long id) {
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener metadatos de paginación", description = "Retorna solo las cabeceras con información de paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Metadatos obtenidos exitosamente")
    })
    @RequestMapping(value = "/beer", method = RequestMethod.HEAD)
    public ResponseEntity<Void> obtenerCabeceras(
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Elementos por página") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BeerResponse> beersPage = beerService.getAllBeers(pageable);
        
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(beersPage.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(beersPage.getTotalPages()))
                .header("X-Current-Page", String.valueOf(beersPage.getNumber()))
                .header("X-Page-Size", String.valueOf(beersPage.getSize()))
                .build();
    }

    @Operation(summary = "Subir imagen de cerveza", description = "Sube una imagen para una cerveza existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen subida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Archivo inválido"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(value = "/beer/{id}/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BeerResponse> subirImagen(
            @Parameter(description = "ID de la cerveza") @PathVariable Long id,
            @Parameter(description = "Archivo de imagen (máx 5MB)") @RequestParam("imagen") MultipartFile imagen) {
        
        BeerResponse cerveza = beerService.getBeerById(id);
        eliminarImagenAnterior(cerveza);
        
        String rutaImagen = fileStorageService.guardarArchivo(imagen);
        UpdateBeerRequest actualizacion = crearActualizacionImagen(rutaImagen);
        
        BeerResponse cervezaActualizada = beerService.updateBeer(id, actualizacion);
        return ResponseEntity.ok(cervezaActualizada);
    }

    private void eliminarImagenAnterior(BeerResponse cerveza) {
        if (cerveza.getRutaArchivo() != null) {
            fileStorageService.eliminarArchivo(cerveza.getRutaArchivo());
        }
    }

    private UpdateBeerRequest crearActualizacionImagen(String rutaImagen) {
        UpdateBeerRequest actualizacion = new UpdateBeerRequest();
        actualizacion.setRutaArchivo(rutaImagen);
        return actualizacion;
    }
}
