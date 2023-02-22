package com.onlinepizza.controllers;

import com.onlinepizza.entities.Pizza;
import com.onlinepizza.exceptions.NotFoundException;
import com.onlinepizza.mappers.PizzaMapper;
import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.repositories.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PizzaControllerIT {

    @Autowired
    PizzaController pizzaController;

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    PizzaMapper pizzaMapper;

    @Test
    void testDeleteByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            pizzaController.deletePizzaById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteById() {
        Pizza pizza = pizzaRepository.findAll().get(0);

        ResponseEntity responseEntity = pizzaController.deletePizzaById(pizza.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(pizzaRepository.findById(pizza.getId())).isEmpty();
    }

    @Test
    void updatePizzaNotFound(){
        assertThrows(NotFoundException.class, () -> {
            pizzaController.updatePizzaById(UUID.randomUUID(), PizzaDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void updatePizza() {
        Pizza pizza = pizzaRepository.findAll().get(0);
        PizzaDTO pizzaDTO = pizzaMapper.pizzaToPizzaDTO(pizza);
        pizzaDTO.setId(null);
        pizzaDTO.setVersion(null);
        final String pizzaName = "Updated Name";
        pizzaDTO.setName(pizzaName);

        ResponseEntity responseEntity = pizzaController.updatePizzaById(pizza.getId(), pizzaDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Pizza updatedPizza = pizzaRepository.findById(pizza.getId()).get();
        assertThat(updatedPizza.getName()).isEqualTo(pizzaName);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewPizza() {
        PizzaDTO pizzaDTO = PizzaDTO.builder()
                .name("New Pizza")
                .build();

        ResponseEntity responseEntity = pizzaController.saveNewPizza(pizzaDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Pizza pizza = pizzaRepository.findById(savedUUID).get();
        assertThat(pizza).isNotNull();
    }

    @Test
    void getByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            pizzaController.getPizzaById(UUID.randomUUID());
        });
    }

    @Test
    void getById() {
        Pizza pizza = pizzaRepository.findAll().get(0);

        PizzaDTO dto = pizzaController.getPizzaById(pizza.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void getPizzaList(){
        List<PizzaDTO> dtos = pizzaController.getPizzaList();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void emptyList() {
        pizzaRepository.deleteAll();
        List<PizzaDTO> dtos = pizzaController.getPizzaList();

        assertThat(dtos.size()).isEqualTo(0);
    }
}