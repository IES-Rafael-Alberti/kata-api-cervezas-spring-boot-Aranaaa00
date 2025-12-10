package com.example.cervezas.repository;

import com.example.cervezas.model.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
    
    Page<Beer> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    
    Page<Beer> findByEstiloId(Long estiloId, Pageable pageable);
    
    Page<Beer> findByCerveceriaId(Long cerveceriaId, Pageable pageable);
}
