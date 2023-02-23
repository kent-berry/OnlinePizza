package com.onlinepizza.services;

import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.models.PizzaStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class PizzaMapService implements PizzaService{

    private Map<UUID, PizzaDTO> pizzaMap;

    public PizzaMapService() {
        this.pizzaMap = new HashMap<>();

        PizzaDTO pizza1 = PizzaDTO.builder()
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

        PizzaDTO pizza2 = PizzaDTO.builder()
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

        PizzaDTO pizza3 = PizzaDTO.builder()
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

        PizzaDTO pizza4 = PizzaDTO.builder()
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

    @Override
    public List<PizzaDTO> getPizzaList(){
        return new ArrayList<>(pizzaMap.values());
    }

    @Override
    public PizzaDTO saveNewPizza(PizzaDTO pizza) {
        log.debug(pizza.getName());
        PizzaDTO savedPizza = PizzaDTO.builder()
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .name(pizza.getName())
                .style(pizza.getStyle())
                .quantityAvailable(pizza.getQuantityAvailable())
                .upc(pizza.getUpc())
                .price(pizza.getPrice())
                .version(pizza.getVersion())
                .build();

        pizzaMap.put(savedPizza.getId(), savedPizza);
        return savedPizza;
    }

    @Override
    public Optional<PizzaDTO> updatePizzaById(UUID id, PizzaDTO pizza) {
        PizzaDTO updatedPizza = pizzaMap.get(id);
        updatedPizza.setName(pizza.getName());
        updatedPizza.setStyle(pizza.getStyle());
        updatedPizza.setPrice(pizza.getPrice());
        updatedPizza.setUpc(pizza.getUpc());
        updatedPizza.setQuantityAvailable(pizza.getQuantityAvailable());
        updatedPizza.setLastUpdated(LocalDateTime.now());

        pizzaMap.put(updatedPizza.getId(), updatedPizza);

        return Optional.of(updatedPizza);
    }

    @Override
    public Boolean deletePizzaById(UUID id) {
        pizzaMap.remove(id);

        //todo impl logic to return false if not found
        return true;
    }

    @Override
    public Optional<PizzaDTO> patchPizzaById(UUID id, PizzaDTO pizza) {
        PizzaDTO patchedPizza = pizzaMap.get(id);

        if (StringUtils.hasText(pizza.getName())){
            patchedPizza.setName(pizza.getName());
        }

        if (pizza.getStyle() != null) {
            patchedPizza.setStyle(pizza.getStyle());
        }

        if (pizza.getPrice() != null) {
            patchedPizza.setPrice(pizza.getPrice());
        }

        if (pizza.getQuantityAvailable() != null) {
            patchedPizza.setQuantityAvailable(pizza.getQuantityAvailable());
        }

        if (StringUtils.hasText(pizza.getUpc())){
            patchedPizza.setUpc(pizza.getUpc());
        }
        return Optional.of(patchedPizza);
    }

    @Override
    public Optional<PizzaDTO> getPizzaById(UUID id) {
        return Optional.of(pizzaMap.get(id));
    }
}
