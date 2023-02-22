package com.onlinepizza.services;

import com.onlinepizza.mappers.PizzaMapper;
import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.repositories.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class PizzaJPAService implements PizzaService{

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    @Override
    public Optional<PizzaDTO> getPizzaById(UUID id) {
        return Optional.ofNullable(pizzaMapper.pizzaToPizzaDTO(pizzaRepository.findById(id).orElse(null)));
    }

    @Override
    public List<PizzaDTO> getPizzaList() {
        return pizzaRepository.findAll()
                .stream()
                .map(pizzaMapper::pizzaToPizzaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PizzaDTO saveNewPizza(PizzaDTO pizza) {
        return pizzaMapper.pizzaToPizzaDTO(pizzaRepository.save(pizzaMapper.pizzaDTOToPizza(pizza)));
    }

    @Override
    public Optional<PizzaDTO> updatePizzaById(UUID id, PizzaDTO pizza) {
        AtomicReference<Optional<PizzaDTO>> atomicReference = new AtomicReference<>();

        pizzaRepository.findById(id).ifPresentOrElse(foundPizza -> {
            foundPizza.setName(pizza.getName());
            foundPizza.setStyle(pizza.getStyle());
            foundPizza.setUpc(pizza.getUpc());
            foundPizza.setPrice(pizza.getPrice());
            atomicReference.set(Optional.of(pizzaMapper.pizzaToPizzaDTO(pizzaRepository.save(foundPizza))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deletePizzaById(UUID id) {
        if (pizzaRepository.existsById(id)){
            pizzaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void patchPizzaById(UUID id, PizzaDTO pizza) {

    }
}
