package com.onlinepizza.services;

import com.onlinepizza.entities.Pizza;
import com.onlinepizza.mappers.PizzaMapper;
import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.models.PizzaStyle;
import com.onlinepizza.repositories.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class PizzaJPAService implements PizzaService{

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 10;

    @Override
    public Optional<PizzaDTO> getPizzaById(UUID id) {
        return Optional.ofNullable(pizzaMapper.pizzaToPizzaDTO(pizzaRepository.findById(id).orElse(null)));
    }

    @Override
    public Page<PizzaDTO> getPizzaList(String name, PizzaStyle style, Boolean showInventory,
                                       Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Pizza> pizzaPage;

        if(StringUtils.hasText(name) && style == null){
            pizzaPage = pizzaRepository.findAllByNameIsLikeIgnoreCase("%" + name + "%", pageRequest);
        } else if(!StringUtils.hasText(name) && style != null){
            pizzaPage = pizzaRepository.findAllByStyle(style, pageRequest);
        } else if(StringUtils.hasText(name) && style != null){
            pizzaPage = pizzaRepository.findAllByNameIsLikeIgnoreCaseAndStyle("%" + name + "%", style, pageRequest);
        } else {
            pizzaPage = pizzaRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory){
            pizzaPage.forEach(pizza -> {
                pizza.setQuantityAvailable(null);
            });
        }

        return pizzaPage.map(pizzaMapper::pizzaToPizzaDTO);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null){
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 200){
                queryPageSize = 200;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("name"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
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
    public Optional<PizzaDTO> patchPizzaById(UUID id, PizzaDTO pizza) {
        AtomicReference<Optional<PizzaDTO>> atomicReference = new AtomicReference<>();

        pizzaRepository.findById(id).ifPresentOrElse(foundPizza -> {
            if (StringUtils.hasText(pizza.getName())){
                foundPizza.setName(pizza.getName());
            }
            if (pizza.getStyle() != null){
                foundPizza.setStyle(pizza.getStyle());
            }
            if (StringUtils.hasText(pizza.getUpc())){
                foundPizza.setUpc(pizza.getUpc());
            }
            if (pizza.getPrice() != null){
                foundPizza.setPrice(pizza.getPrice());
            }
            if (pizza.getQuantityAvailable() != null){
                foundPizza.setQuantityAvailable(pizza.getQuantityAvailable());
            }
            atomicReference.set(Optional.of(pizzaMapper
                    .pizzaToPizzaDTO(pizzaRepository.save(foundPizza))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
