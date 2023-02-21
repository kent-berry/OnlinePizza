package com.onlinepizza.services;

import com.onlinepizza.models.CustomerDTO;
import com.onlinepizza.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerJPAService implements CustomerService{

    private final CustomerRepository customerRepository;


    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<CustomerDTO> getCustomerList() {
        return null;
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomerById(UUID id, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomerById(UUID id) {

    }

    @Override
    public void patchCustomerById(UUID id, CustomerDTO customer) {

    }
}
