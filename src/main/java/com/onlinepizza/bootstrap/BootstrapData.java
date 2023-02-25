package com.onlinepizza.bootstrap;

import com.onlinepizza.entities.Customer;
import com.onlinepizza.entities.Pizza;
import com.onlinepizza.models.PizzaCSVRecord;
import com.onlinepizza.models.PizzaStyle;
import com.onlinepizza.repositories.CustomerRepository;
import com.onlinepizza.repositories.PizzaRepository;
import com.onlinepizza.services.PizzaCSVService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final PizzaRepository pizzaRepository;
    private final CustomerRepository customerRepository;
    private final PizzaCSVService pizzaCSVService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadPizzaData();
        loadCSVData();
        loadCustomerData();
    }

    private void loadCSVData() throws FileNotFoundException {
        if (pizzaRepository.count() < 100){
            File file = ResourceUtils.getFile("classpath:csvdata/pizzas.csv");

            List<PizzaCSVRecord> csvRecordList = pizzaCSVService.convertCSV(file);

            csvRecordList.forEach(pizzaCSVRecord -> {
                PizzaStyle pizzaStyle = switch (pizzaCSVRecord.getStyle()) {
                    case "SICILIAN" -> PizzaStyle.SICILIAN;
                    case "NYC" -> PizzaStyle.NYC;
                    case "CHICAGO" -> PizzaStyle.CHICAGO;
                    case "DETROIT" -> PizzaStyle.DETROIT;
                    case "NEOPOLITAN" -> PizzaStyle.NEOPOLITAN;
                    default -> PizzaStyle.NYC;
                };

                pizzaRepository.save(Pizza.builder()
                        .name(pizzaCSVRecord.getName())
                        .style(pizzaStyle)
                        .price(new BigDecimal(pizzaCSVRecord.getPrice()))
                        .quantityAvailable(pizzaCSVRecord.getQuantity())
                        .upc(pizzaCSVRecord.getUpc())
                        .build());
            });
        }
    }

    private void loadPizzaData() {
        if (pizzaRepository.count() == 0) {
            Pizza pizza1 = Pizza.builder()
                    .name("Dollar Pizza")
                    .style(PizzaStyle.NYC)
                    .upc("12356")
                    .price(new BigDecimal("1.99"))
                    .quantityAvailable(100)
                    .build();

            Pizza pizza2 = Pizza.builder()
                    .name("That's a spicy meat-a-balll")
                    .style(PizzaStyle.SICILIAN)
                    .upc("12356222")
                    .price(new BigDecimal("15.75"))
                    .quantityAvailable(69)
                    .build();

            Pizza pizza3 = Pizza.builder()
                    .name("POOL OF SAUCE")
                    .style(PizzaStyle.CHICAGO)
                    .upc("1235623")
                    .price(new BigDecimal("15.99"))
                    .quantityAvailable(0)
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
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer 2")
                    .version(1)
                    .build();

            Customer customer3 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer 3")
                    .version(1)
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }

    }
}