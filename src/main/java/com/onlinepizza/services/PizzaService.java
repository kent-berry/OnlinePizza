package com.onlinepizza.services;

import com.onlinepizza.models.PizzaDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PizzaService {
    Optional<PizzaDTO> getPizzaById(UUID id);

    List<PizzaDTO> getPizzaList();

    PizzaDTO saveNewPizza(PizzaDTO pizza);

    Optional<PizzaDTO> updatePizzaById(UUID id, PizzaDTO pizza);

    Boolean deletePizzaById(UUID id);

    Optional<PizzaDTO> patchPizzaById(UUID id, PizzaDTO pizza);
}
