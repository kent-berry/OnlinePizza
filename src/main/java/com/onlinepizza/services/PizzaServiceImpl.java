package com.onlinepizza.services;

import com.onlinepizza.models.Pizza;
import com.onlinepizza.models.PizzaStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class PizzaServiceImpl implements PizzaService{

    private Map<UUID, Pizza> pizzaMap;

    public PizzaServiceImpl() {
        this.pizzaMap = new HashMap<>();

        Pizza pizza1 = Pizza.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Margherita")
                .style(PizzaStyle.NEOPOLITAN)
                .upc("123")
                .price(new BigDecimal("15.99"))
                .quantityAvailable(50)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        Pizza pizza2 = Pizza.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("NYC Pepperoni")
                .style(PizzaStyle.NYC)
                .upc("1231")
                .price(new BigDecimal("12.99"))
                .quantityAvailable(25)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        Pizza pizza3 = Pizza.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Spicy Sicilian")
                .style(PizzaStyle.SICILIAN)
                .upc("12312")
                .price(new BigDecimal("19.99"))
                .quantityAvailable(12)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        Pizza pizza4 = Pizza.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("More Lasagna Than Pizza")
                .style(PizzaStyle.CHICAGO)
                .upc("12312")
                .price(new BigDecimal("0.05"))
                .quantityAvailable(12)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        pizzaMap.put(pizza1.getId(), pizza1);
        pizzaMap.put(pizza2.getId(), pizza2);
        pizzaMap.put(pizza3.getId(), pizza3);
        pizzaMap.put(pizza4.getId(), pizza4);
    }

    public List<Pizza> listPizza(){
        return new ArrayList<>(pizzaMap.values());
    }

    @Override
    public Pizza getPizzaById(UUID id) {
        return pizzaMap.get(id);
    }
}
