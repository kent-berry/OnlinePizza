package com.onlinepizza.controllers;

import com.onlinepizza.exceptions.NotFoundException;
import com.onlinepizza.models.PizzaDTO;
import com.onlinepizza.models.PizzaStyle;
import com.onlinepizza.services.PizzaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PizzaController {

    public static final String PIZZA_PATH = "/api/v1/pizza";
    public static final String PIZZA_PATH_ID = PIZZA_PATH + "/{id}";

    private final PizzaService pizzaService;

    @PatchMapping(PIZZA_PATH_ID)
    public ResponseEntity patchPizzaById(@PathVariable("id") UUID id, @RequestBody PizzaDTO pizza){

        pizzaService.patchPizzaById(id, pizza);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PIZZA_PATH_ID)
    public ResponseEntity deletePizzaById(@PathVariable("id") UUID id){

        if (!pizzaService.deletePizzaById(id)){
            throw new NotFoundException();
        };

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(PIZZA_PATH_ID)
    public ResponseEntity updatePizzaById(@PathVariable("id") UUID id, @Validated @RequestBody PizzaDTO pizza){

        if(pizzaService.updatePizzaById(id, pizza).isEmpty()){
            throw new NotFoundException();
        };

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(PIZZA_PATH)
    public ResponseEntity saveNewPizza(@Validated @RequestBody PizzaDTO pizza){
        PizzaDTO savedPizza = pizzaService.saveNewPizza(pizza);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/pizza/" + savedPizza.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(PIZZA_PATH)
    public Page<PizzaDTO> getPizzaList(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) PizzaStyle style,
                                       @RequestParam(required = false) Boolean showInventory,
                                       @RequestParam(required = false) Integer pageNumber,
                                       @RequestParam(required = false) Integer pageSize){
        return pizzaService.getPizzaList(name, style, showInventory, 1, 10);
    }

    @GetMapping(PIZZA_PATH_ID)
    public PizzaDTO getPizzaById(@PathVariable UUID id){
        return pizzaService.getPizzaById(id).orElseThrow(NotFoundException::new);
    }
}
