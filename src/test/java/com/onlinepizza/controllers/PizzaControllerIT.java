package com.onlinepizza.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepizza.entities.Pizza;
import com.onlinepizza.exceptions.NotFoundException;
import com.onlinepizza.mappers.PizzaMapper;
import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.repositories.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.onlinepizza.controllers.PizzaController.PIZZA_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PizzaControllerIT {

    @Autowired
    PizzaController pizzaController;

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    PizzaMapper pizzaMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void patchPizzaNameTooLong() throws Exception {
        Pizza testPizza = pizzaRepository.findAll().get(0);

        Map<String, Object> pizzaMap = new HashMap<>();
        pizzaMap.put("name", "New Name2134413798sdfgsgfsfd97g9s8df7g78907979087907097987s9dg87sd9fdgs7dgf9ddsfasdfj23515321235dg79");

        MvcResult mvcResult = mockMvc.perform(patch(PIZZA_PATH_ID, testPizza.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizzaMap))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

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