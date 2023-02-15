package com.onlinepizza.controllers;

import com.onlinepizza.models.Pizza;
import com.onlinepizza.services.PizzaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class PizzaController {
    private final PizzaService pizzaService;

    public Pizza getPizzaById(UUID id){
        return pizzaService.getPizzaById(id);
    }
}
