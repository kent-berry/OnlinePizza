package com.onlinepizza.controllers;

import com.onlinepizza.models.Pizza;
import com.onlinepizza.services.PizzaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class PizzaController {
    private final PizzaService pizzaService;

    @RequestMapping("/api/v1/pizza")
    public List<Pizza> listPizza(){
        return pizzaService.listPizza();
    }

    public Pizza getPizzaById(UUID id){
        return pizzaService.getPizzaById(id);
    }
}
