package com.onlinepizza.services;

import com.onlinepizza.models.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    public Optional<CustomerDTO> getCustomerById(UUID id);

    public List<CustomerDTO> getCustomerList();

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer);

    Boolean deleteCustomerById(UUID id);

    void patchCustomerById(UUID id, CustomerDTO customer);
}
