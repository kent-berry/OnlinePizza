package com.onlinepizza.services;

import com.onlinepizza.models.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerMapService implements CustomerService{

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerMapService(){
        customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Bob Robert")
                .version(1)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Robert Bob")
                .version(1)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
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
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public List<CustomerDTO> getCustomerList() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
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
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer) {
        CustomerDTO updatedCustomer = customerMap.get(id);
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setLastUpdated(LocalDateTime.now());

        customerMap.put(updatedCustomer.getId(), updatedCustomer);

        return Optional.of(updatedCustomer);
    }

    @Override
    public Boolean deleteCustomerById(UUID id) {
        customerMap.remove(id);

        //todo impl logic to return false if not found
        return true;
    }

    @Override
    public void patchCustomerById(UUID id, CustomerDTO customer) {
        CustomerDTO patchedCustomer = customerMap.get(id);

        if (StringUtils.hasText(customer.getName())){
            patchedCustomer.setName(customer.getName());
        }
    }
}
