package com.example.cervezas.service;

import com.example.cervezas.dto.BeerResponse;
import com.example.cervezas.dto.CreateBeerRequest;
import com.example.cervezas.dto.UpdateBeerRequest;
import com.example.cervezas.exception.RecursoNoEncontradoException;
import com.example.cervezas.model.Beer;
import com.example.cervezas.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BeerService {

    private final BeerRepository beerRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public BeerResponse createBeer(CreateBeerRequest request) {
        Beer beer = mapToBeer(new Beer(), request);
        beer.setUltimaModificacion(LocalDateTime.now());
        Beer beerGuardada = beerRepository.save(beer);
        return convertToResponse(beerGuardada);
    }

    @Transactional(readOnly = true)
    public Page<BeerResponse> getAllBeers(Pageable pageable) {
        return beerRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    public BeerResponse getBeerById(Long id) {
        Beer beer = findBeerOrThrow(id);
        return convertToResponse(beer);
    }

    @Transactional(readOnly = true)
    public Page<BeerResponse> searchBeersByName(String name, Pageable pageable) {
        return beerRepository.findByNombreContainingIgnoreCase(name, pageable)
                .map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    public Page<BeerResponse> getBeersByStyle(Long styleId, Pageable pageable) {
        return beerRepository.findByEstiloId(styleId, pageable)
                .map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    public Page<BeerResponse> getBeersByBrewery(Long breweryId, Pageable pageable) {
        return beerRepository.findByCerveceriaId(breweryId, pageable)
                .map(this::convertToResponse);
    }

    @Transactional
    public BeerResponse updateBeer(Long id, UpdateBeerRequest request) {
        Beer beer = findBeerOrThrow(id);
        updateBeerFields(beer, request);
        beer.setUltimaModificacion(LocalDateTime.now());
        Beer beerActualizada = beerRepository.save(beer);
        return convertToResponse(beerActualizada);
    }

    @Transactional
    public void deleteBeer(Long id) {
        Beer cerveza = findBeerOrThrow(id);
        eliminarImagenAsociada(cerveza);
        beerRepository.deleteById(id);
    }

    private void eliminarImagenAsociada(Beer cerveza) {
        if (cerveza.getRutaArchivo() != null) {
            fileStorageService.eliminarArchivo(cerveza.getRutaArchivo());
        }
    }

    private Beer findBeerOrThrow(Long id) {
        return beerRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cerveza", id));
    }

    private Beer mapToBeer(Beer beer, CreateBeerRequest request) {
        beer.setCerveceriaId(request.getCerveceriaId());
        beer.setNombre(request.getNombre());
        beer.setCategoriaId(request.getCategoriaId());
        beer.setEstiloId(request.getEstiloId());
        beer.setGraduacionAlcoholica(request.getGraduacionAlcoholica());
        beer.setIbu(request.getIbu());
        beer.setSrm(request.getSrm());
        beer.setUpc(request.getUpc());
        beer.setRutaArchivo(request.getRutaArchivo());
        beer.setDescripcion(request.getDescripcion());
        beer.setUsuarioAgregador(request.getUsuarioAgregador());
        return beer;
    }

    private void updateBeerFields(Beer beer, UpdateBeerRequest request) {
        if (request.getCerveceriaId() != null) beer.setCerveceriaId(request.getCerveceriaId());
        if (request.getNombre() != null) beer.setNombre(request.getNombre());
        if (request.getCategoriaId() != null) beer.setCategoriaId(request.getCategoriaId());
        if (request.getEstiloId() != null) beer.setEstiloId(request.getEstiloId());
        if (request.getGraduacionAlcoholica() != null) beer.setGraduacionAlcoholica(request.getGraduacionAlcoholica());
        if (request.getIbu() != null) beer.setIbu(request.getIbu());
        if (request.getSrm() != null) beer.setSrm(request.getSrm());
        if (request.getUpc() != null) beer.setUpc(request.getUpc());
        if (request.getRutaArchivo() != null) beer.setRutaArchivo(request.getRutaArchivo());
        if (request.getDescripcion() != null) beer.setDescripcion(request.getDescripcion());
        if (request.getUsuarioAgregador() != null) beer.setUsuarioAgregador(request.getUsuarioAgregador());
    }

    private BeerResponse convertToResponse(Beer beer) {
        return new BeerResponse(
            beer.getId(),
            beer.getCerveceriaId(),
            beer.getNombre(),
            beer.getCategoriaId(),
            beer.getEstiloId(),
            beer.getGraduacionAlcoholica(),
            beer.getIbu(),
            beer.getSrm(),
            beer.getUpc(),
            beer.getRutaArchivo(),
            beer.getDescripcion(),
            beer.getUsuarioAgregador(),
            beer.getUltimaModificacion()
        );
    }
}
