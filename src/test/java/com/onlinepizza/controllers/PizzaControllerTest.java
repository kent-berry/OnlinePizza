package com.onlinepizza.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.services.PizzaMapService;
import com.onlinepizza.services.PizzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.onlinepizza.controllers.PizzaController.PIZZA_PATH;
import static com.onlinepizza.controllers.PizzaController.PIZZA_PATH_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(PizzaController.class)
class PizzaControllerTest {

//    @Autowired
//    PizzaController pizzaController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PizzaService pizzaService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<PizzaDTO> pizzaArgumentCaptor;

    PizzaMapService pizzaMapService = new PizzaMapService();

    PizzaDTO testPizza;

    @Test
    void getPizzaByIdNotFound() throws Exception {

        given(pizzaService.getPizzaById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(PIZZA_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPizzaById() throws Exception {
        PizzaDTO testPizza = pizzaMapService.getPizzaList().get(0);

        given(pizzaService.getPizzaById(testPizza.getId())).willReturn(Optional.of(testPizza));

        mockMvc.perform(get(PIZZA_PATH + "/" + testPizza.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testPizza.getId().toString())))
                .andExpect(jsonPath("$.name", is(testPizza.getName())));
    }

    @BeforeEach
    void setUp() {
        pizzaMapService = new PizzaMapService();
        testPizza = pizzaMapService.getPizzaList().get(0);
    }

    @Test
    void patchPizzaById() throws Exception {
        Map<String, Object> pizzaMap = new HashMap<>();
        pizzaMap.put("name", "New Name");

        mockMvc.perform(patch(PIZZA_PATH_ID, testPizza.getId())
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizzaMap))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(pizzaService).patchPizzaById(uuidArgumentCaptor.capture(), pizzaArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testPizza.getId());
        assertThat(pizzaArgumentCaptor.getValue().getName()).isEqualTo(pizzaMap.get("name"));
    }

    @Test
    void deletePizzaById() throws Exception {
        given(pizzaService.deletePizzaById(any())).willReturn(true);

        mockMvc.perform(delete(PIZZA_PATH_ID, testPizza.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(pizzaService).deletePizzaById(uuidArgumentCaptor.capture());

        assertThat(testPizza.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void updatePizzaById() throws Exception {
        given(pizzaService.updatePizzaById(any(), any())).willReturn(Optional.of(testPizza));

        mockMvc.perform(put(PIZZA_PATH_ID, testPizza.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPizza)))
                .andExpect(status().isNoContent());

        verify(pizzaService).updatePizzaById(uuidArgumentCaptor.capture(), any(PizzaDTO.class));

        assertThat(testPizza.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void saveNewPizza() throws Exception {
        testPizza.setId(null);
        testPizza.setVersion(null);

        given(pizzaService.saveNewPizza(any(PizzaDTO.class))).willReturn(pizzaMapService.getPizzaList().get(1));

        mockMvc.perform(post(PIZZA_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testPizza)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getPizzaList() throws Exception {
        given(pizzaService.getPizzaList()).willReturn(pizzaMapService.getPizzaList());

        mockMvc.perform(get(PIZZA_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(4)));
    }
}