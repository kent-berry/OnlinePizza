package com.onlinepizza.services;

import com.onlinepizza.models.Pizza;

import java.util.List;
import java.util.UUID;

public interface PizzaService {
    Pizza getPizzaById(UUID id);

    List<Pizza> listPizza();
}
