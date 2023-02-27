package com.onlinepizza.services;

import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.models.PizzaStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface PizzaService {
    Optional<PizzaDTO> getPizzaById(UUID id);

    Page<PizzaDTO> getPizzaList(String name, PizzaStyle style, Boolean showInventory, Integer pageNumber, Integer pageSize);

    PizzaDTO saveNewPizza(PizzaDTO pizza);

    Optional<PizzaDTO> updatePizzaById(UUID id, PizzaDTO pizza);

    Boolean deletePizzaById(UUID id);

    Optional<PizzaDTO> patchPizzaById(UUID id, PizzaDTO pizza);
}
