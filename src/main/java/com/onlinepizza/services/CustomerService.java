package com.onlinepizza.services;

import com.onlinepizza.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    public Customer getCustomerById(UUID id);

    public List<Customer> getCustomerList();

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID id, Customer customer);

    void deleteCustomerById(UUID id);

    void patchCustomerById(UUID id, Customer customer);
}
