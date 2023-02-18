package com.onlinepizza.services;

import com.onlinepizza.models.Pizza;

import java.util.List;
import java.util.UUID;

public interface PizzaService {
    Pizza getPizzaById(UUID id);

    List<Pizza> getPizzaList();

    Pizza saveNewPizza(Pizza pizza);

    void updatePizzaById(UUID id, Pizza pizza);

    void deletePizzaById(UUID id);

    void patchPizzaById(UUID id, Pizza pizza);
}
