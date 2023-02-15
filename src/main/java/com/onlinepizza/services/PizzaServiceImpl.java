package com.onlinepizza.services;

import com.onlinepizza.models.Pizza;
import com.onlinepizza.models.PizzaStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class PizzaServiceImpl implements PizzaService{
    @Override
    public Pizza getPizzaById(UUID id) {
        return Pizza.builder()
                .id(id)
                .version(1)
                .name("Margherita")
                .style(PizzaStyle.NEOPOLITAN)
                .upc("123")
                .price(new BigDecimal("15.99"))
                .quantityAvailable(50)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
    }
}
