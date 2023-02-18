package com.onlinepizza.controllers;

import com.onlinepizza.models.Pizza;
import com.onlinepizza.services.PizzaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pizza")
public class PizzaController {

    private final PizzaService pizzaService;

    @PatchMapping("/{id}")
    public ResponseEntity patchPizzaById(@PathVariable("id") UUID id, @RequestBody Pizza pizza){

        pizzaService.patchPizzaById(id, pizza);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomerById(@PathVariable("id") UUID id){

        pizzaService.deletePizzaById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePizzaById(@PathVariable("id") UUID id, @RequestBody Pizza pizza){

        pizzaService.updatePizzaById(id, pizza);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Pizza pizza){
        Pizza savedPizza = pizzaService.saveNewPizza(pizza);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/pizza/" + savedPizza.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Pizza> getPizzaList(){
        log.debug("test123");
        return pizzaService.getPizzaList();
    }

    @GetMapping("/{id}")
    public Pizza getPizzaById(@PathVariable UUID id){
        return pizzaService.getPizzaById(id);
    }
}
