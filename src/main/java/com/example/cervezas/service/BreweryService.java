package com.example.cervezas.service;

import com.example.cervezas.exception.RecursoNoEncontradoException;
import com.example.cervezas.model.Brewery;
import com.example.cervezas.repository.BreweryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BreweryService {

    private final BreweryRepository breweryRepository;

    @Transactional(readOnly = true)
    public Page<Brewery> obtenerTodas(Pageable pageable) {
        return breweryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Brewery obtenerPorId(Long id) {
        return breweryRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cervecería", id));
    }
}
