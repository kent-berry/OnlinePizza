package com.onlinepizza.controllers;

import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.repositories.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PizzaControllerIT {

    @Autowired
    PizzaController pizzaController;

    @Autowired
    PizzaRepository pizzaRepository;

    @Test
    void testGetPizzaList(){
        List<PizzaDTO> dtos = pizzaController.getPizzaList();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        pizzaRepository.deleteAll();
        List<PizzaDTO> dtos = pizzaController.getPizzaList();

        assertThat(dtos.size()).isEqualTo(0);
    }
}