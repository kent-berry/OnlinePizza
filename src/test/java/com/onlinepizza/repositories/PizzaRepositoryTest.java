package com.onlinepizza.repositories;

import com.onlinepizza.entities.Pizza;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PizzaRepositoryTest {

    @Autowired
    PizzaRepository pizzaRepository;

    @Test
    void testSavePizza() {
        Pizza savedPizza = pizzaRepository.save(Pizza.builder().name("Best Pizza").build());

        assertThat(savedPizza).isNotNull();
        assertThat(savedPizza.getId()).isNotNull();
    }

}