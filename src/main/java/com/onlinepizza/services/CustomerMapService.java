package com.onlinepizza.services;

import com.onlinepizza.models.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerMapService implements CustomerService{

    private Map<UUID, Customer> customerMap;

    public CustomerMapService(){
        customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Bob Robert")
                .version(1)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Robert Bob")
                .version(1)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Rob Bobert")
                .version(1)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public List<Customer> getCustomerList() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .version(customer.getVersion())
                .name(customer.getName())
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID id, Customer customer) {
        Customer updatedCustomer = customerMap.get(id);
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setLastUpdated(LocalDateTime.now());
        updatedCustomer.setVersion(updatedCustomer.getVersion() + 1);

        customerMap.put(updatedCustomer.getId(), updatedCustomer);
    }

    @Override
    public void deleteCustomerById(UUID id) {
        customerMap.remove(id);
    }

    @Override
    public void patchCustomerById(UUID id, Customer customer) {
        Customer patchedCustomer = customerMap.get(id);

        if (StringUtils.hasText(customer.getName())){
            patchedCustomer.setName(customer.getName());
        }
    }
}
