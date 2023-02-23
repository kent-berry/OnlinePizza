package com.onlinepizza.repositories;

import com.onlinepizza.entities.Pizza;
import com.onlinepizza.models.PizzaStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class PizzaRepositoryTest {

    @Autowired
    PizzaRepository pizzaRepository;

    @Test
    void testSavePizzaNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Pizza savedPizza = pizzaRepository.save(Pizza.builder()
                    .name("Best Pizza 124354879327235452oui5o2u35928559dfsfvvsxc9695672323523523523523532529")
                    .style(PizzaStyle.CHICAGO)
                    .upc("1235454")
                    .price(new BigDecimal(15.95))
                    .build());

            pizzaRepository.flush();
        });
    }

    @Test
    void testSavePizza() {
        Pizza savedPizza = pizzaRepository.save(Pizza.builder()
                .name("Best Pizza")
                .style(PizzaStyle.CHICAGO)
                .upc("1235454")
                .price(new BigDecimal(15.95))
                .build());

        pizzaRepository.flush();

        assertThat(savedPizza).isNotNull();
        assertThat(savedPizza.getId()).isNotNull();
    }

}