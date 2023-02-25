package com.onlinepizza.repositories;

import com.onlinepizza.entities.Pizza;
import com.onlinepizza.models.PizzaStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PizzaRepository extends JpaRepository<Pizza, UUID> {
    List<Pizza> findAllByNameIsLikeIgnoreCase(String name);
    List<Pizza> findAllByStyle(PizzaStyle style);
    List<Pizza> findAllByNameIsLikeIgnoreCaseAndStyle(String name, PizzaStyle style);
}
