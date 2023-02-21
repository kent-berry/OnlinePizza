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
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class PizzaJPAService implements PizzaService{

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    @Override
    public Optional<PizzaDTO> getPizzaById(UUID id) {
        return Optional.of(pizzaMapper.pizzaToPizzaDTO(pizzaRepository.findById(id).orElse(null)));
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
        return null;
    }

    @Override
    public void updatePizzaById(UUID id, PizzaDTO pizza) {

    }

    @Override
    public void deletePizzaById(UUID id) {

    }

    @Override
    public void patchPizzaById(UUID id, PizzaDTO pizza) {

    }
}
