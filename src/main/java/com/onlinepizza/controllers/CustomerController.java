package com.onlinepizza.controllers;

import com.onlinepizza.models.Customer;
import com.onlinepizza.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PatchMapping("/{id}")
    public ResponseEntity patchCustomerById(@PathVariable("id") UUID id, @RequestBody Customer customer){

        customerService.patchCustomerById(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomerById(@PathVariable("id") UUID id){

        customerService.deleteCustomerById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCustomerById(@PathVariable("id") UUID id, @RequestBody Customer customer){

        customerService.updateCustomerById(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping()
    public List<Customer> getCustomerList(){
        return customerService.getCustomerList();
    }

    @GetMapping("/{id}")
    public Customer getPizzaById(@PathVariable UUID id){
        return customerService.getCustomerById(id);
    }
}
