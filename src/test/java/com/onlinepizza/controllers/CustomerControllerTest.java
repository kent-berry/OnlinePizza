package com.onlinepizza.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepizza.config.SpringSecurityConfig;
import com.onlinepizza.models.CustomerDTO;
import com.onlinepizza.services.CustomerMapService;
import com.onlinepizza.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.onlinepizza.controllers.CustomerController.CUSTOMER_PATH;
import static com.onlinepizza.controllers.CustomerController.CUSTOMER_PATH_ID;
import static com.onlinepizza.controllers.PizzaControllerTest.PASSWORD;
import static com.onlinepizza.controllers.PizzaControllerTest.USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@Import(SpringSecurityConfig.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    CustomerMapService customerMapService = new CustomerMapService();

    CustomerDTO testCustomer;

    @BeforeEach
    void setUp() {
        customerMapService = new CustomerMapService();
        testCustomer = customerMapService.getCustomerList().get(0);
    }

    @Test
    void patchCustomerById() throws Exception {
        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("name", "New Name");

        mockMvc.perform(patch(CUSTOMER_PATH + "/" + testCustomer.getId())
                        .with(httpBasic(USER, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testCustomer.getId());
        assertThat(customerArgumentCaptor.getValue().getName()).isEqualTo(customerMap.get("name"));
    }

    @Test
    void deleteCustomerById() throws Exception {
        given(customerService.deleteCustomerById(any())).willReturn(true);

        mockMvc.perform(delete(CUSTOMER_PATH_ID, testCustomer.getId())
                        .with(httpBasic(USER, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());

        assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void updateCustomerById() throws Exception {
        given(customerService.updateCustomerById(any(), any())).willReturn(Optional.of(testCustomer));

        mockMvc.perform(put(CUSTOMER_PATH_ID, testCustomer.getId())
                        .with(httpBasic(USER, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), any(CustomerDTO.class));

        assertThat(testCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void saveNewCustomer() throws Exception {
        testCustomer.setId(null);
        testCustomer.setVersion(null);

        given(customerService.saveNewCustomer(any(CustomerDTO.class))).willReturn(customerMapService.getCustomerList().get(1));

        mockMvc.perform(post(CUSTOMER_PATH)
                        .with(httpBasic(USER, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getCustomerList() throws Exception {
        given(customerService.getCustomerList()).willReturn(customerMapService.getCustomerList());

        mockMvc.perform(get(CUSTOMER_PATH)
                        .with(httpBasic(USER, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CUSTOMER_PATH_ID, UUID.randomUUID())
                        .with(httpBasic(USER, PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerById() throws Exception {
        given(customerService.getCustomerById(testCustomer.getId())).willReturn(Optional.of(testCustomer));

        mockMvc.perform(get(CUSTOMER_PATH_ID, testCustomer.getId())
                        .with(httpBasic(USER, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())));
    }
}