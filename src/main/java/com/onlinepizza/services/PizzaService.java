package com.onlinepizza.services;

import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.models.PizzaStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PizzaService {
    Optional<PizzaDTO> getPizzaById(UUID id);

    List<PizzaDTO> getPizzaList(String name, PizzaStyle style, Boolean showInventory);

    PizzaDTO saveNewPizza(PizzaDTO pizza);

    Optional<PizzaDTO> updatePizzaById(UUID id, PizzaDTO pizza);

    Boolean deletePizzaById(UUID id);

    Optional<PizzaDTO> patchPizzaById(UUID id, PizzaDTO pizza);
}
