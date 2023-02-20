package com.onlinepizza.bootstrap;

import com.onlinepizza.entities.Customer;
import com.onlinepizza.entities.Pizza;
import com.onlinepizza.models.PizzaStyle;
import com.onlinepizza.repositories.CustomerRepository;
import com.onlinepizza.repositories.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final PizzaRepository pizzaRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadPizzaData();
        loadCustomerData();
    }

    private void loadPizzaData() {
        if (pizzaRepository.count() == 0){
            Pizza pizza1 = Pizza.builder()
                    .name("Dollar Pizza")
                    .style(PizzaStyle.NYC)
                    .upc("12356")
                    .price(new BigDecimal("1.99"))
                    .quantityAvailable(100)
                    .created(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now())
                    .build();

            Pizza pizza2 = Pizza.builder()
                    .name("That's a spicy meat-a-balll")
                    .style(PizzaStyle.SICILIAN)
                    .upc("12356222")
                    .price(new BigDecimal("15.75"))
                    .quantityAvailable(69)
                    .created(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now())
                    .build();

            Pizza pizza3 = Pizza.builder()
                    .name("POOL OF SAUCE")
                    .style(PizzaStyle.CHICAGO)
                    .upc("1235623")
                    .price(new BigDecimal("15.99"))
                    .quantityAvailable(0)
                    .created(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now())
                    .build();

            pizzaRepository.save(pizza1);
            pizzaRepository.save(pizza2);
            pizzaRepository.save(pizza3);
        }

    }

    private void loadCustomerData() {

        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer 1")
                    .version(1)
                    .created(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer 2")
                    .version(1)
                    .created(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer 3")
                    .version(1)
                    .created(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }

    }