package com.onlinepizza.controllers;

import com.onlinepizza.models.Pizza;
import com.onlinepizza.services.PizzaMapService;
import com.onlinepizza.services.PizzaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(PizzaController.class)
class PizzaControllerTest {

//    @Autowired
//    PizzaController pizzaController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PizzaService pizzaService;

    PizzaMapService pizzaMapService = new PizzaMapService();

    @Test
    void getPizzaById() throws Exception {
        Pizza testPizza = pizzaMapService.getPizzaList().get(0);

        given(pizzaService.getPizzaById(testPizza.getId())).willReturn(testPizza);

        mockMvc.perform(get("/api/v1/pizza/" + testPizza.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testPizza.getId().toString())))
                .andExpect(jsonPath("$.name", is(testPizza.getName())));
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void patchPizzaById() {
    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void updatePizzaById() {
    }

    @Test
    void handlePost() {
    }

    @Test
    void getPizzaList() throws Exception {
        given(pizzaService.getPizzaList()).willReturn(pizzaMapService.getPizzaList());

        mockMvc.perform(get("/api/v1/pizza")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(4)));
    }
}