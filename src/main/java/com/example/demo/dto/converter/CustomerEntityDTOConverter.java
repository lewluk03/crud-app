package com.example.demo.dto.converter;

import com.example.demo.dto.AddModCustomerDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityDTOConverter {

    public CustomerDTO convertEntity(Customer customer){
        return new CustomerDTO(customer.getCustomerId(), customer.getFirstName(), customer.getLastName());
    }

    public Customer convertAdModCustomerDTO(AddModCustomerDTO addModCustomerDTO){
        Customer customer=  new Customer();
        customer.setLastName(addModCustomerDTO.getLastName());
        customer.setFirstName(addModCustomerDTO.getFirstName());
        return customer;
    }
}
