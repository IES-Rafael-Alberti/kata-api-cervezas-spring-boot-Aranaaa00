package com.example.cervezas.service;

import com.example.cervezas.exception.RecursoNoEncontradoException;
import com.example.cervezas.model.Category;
import com.example.cervezas.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<Category> obtenerTodas(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Category obtenerPorId(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría", id));
    }
}
