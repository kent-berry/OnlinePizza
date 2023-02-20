package com.onlinepizza.services;

import com.onlinepizza.models.PizzaDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PizzaService {
    Optional<PizzaDTO> getPizzaById(UUID id);

    List<PizzaDTO> getPizzaList();

    PizzaDTO saveNewPizza(PizzaDTO pizza);

    void updatePizzaById(UUID id, PizzaDTO pizza);

    void deletePizzaById(UUID id);

    void patchPizzaById(UUID id, PizzaDTO pizza);
}
