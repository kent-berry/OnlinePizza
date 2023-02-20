package com.onlinepizza.mappers;

import com.onlinepizza.entities.Pizza;
import com.onlinepizza.models.PizzaDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PizzaMapper {

    Pizza pizzaDTOToPizza(PizzaDTO dto);

    PizzaDTO pizzaToPizzaDTO(Pizza pizza);
}
