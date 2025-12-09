package com.example.cervezas.service;

import com.example.cervezas.exception.RecursoNoEncontradoException;
import com.example.cervezas.model.Style;
import com.example.cervezas.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StyleService {

    private final StyleRepository styleRepository;

    @Transactional(readOnly = true)
    public Page<Style> obtenerTodos(Pageable pageable) {
        return styleRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Style obtenerPorId(Long id) {
        return styleRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estilo", id));
    }
}
