package com.onlinepizza.repositories;

import com.onlinepizza.entities.Customer;
import com.onlinepizza.entities.Pizza;
import com.onlinepizza.entities.PizzaOrder;
import com.onlinepizza.entities.PizzaOrderShipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PizzaOrderRepositoryTest {

    @Autowired
    PizzaOrderRepository pizzaOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    Customer testCustomer;
    Pizza testPizza;

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testPizza = pizzaRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testPizzaOrders(){
        PizzaOrder pizzaOrder = PizzaOrder.builder()
                .customerRef("Test Order")
                .customer(testCustomer)
                .pizzaOrderShipment(PizzaOrderShipment.builder().trackingNumber("12345").build())
                .build();

        PizzaOrder savedPizzaOrder = pizzaOrderRepository.save(pizzaOrder);

        System.out.println(savedPizzaOrder.getCustomerRef());
    }
}