package com.onlinepizza.repositories;

import com.onlinepizza.entities.Pizza;
import com.onlinepizza.models.PizzaStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PizzaRepository extends JpaRepository<Pizza, UUID> {
    Page<Pizza> findAllByNameIsLikeIgnoreCase(String name, Pageable pageable);
    Page<Pizza> findAllByStyle(PizzaStyle style, Pageable pageable);
    Page<Pizza> findAllByNameIsLikeIgnoreCaseAndStyle(String name, PizzaStyle style, Pageable pageable);
}
