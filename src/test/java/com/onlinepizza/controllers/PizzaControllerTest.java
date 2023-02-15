package com.onlinepizza.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzaControllerTest {

    @Autowired
    PizzaController pizzaController;

    @Test
    void getPizzaById(){
        System.out.println(pizzaController.getPizzaById(UUID.randomUUID()));
    }

}