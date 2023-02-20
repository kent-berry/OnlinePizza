package com.onlinepizza.mappers;

import com.onlinepizza.entities.Customer;
import com.onlinepizza.models.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDTOToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
