package com.example.demo.dto.converter;

import com.example.demo.dto.AddModCustomerDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.Customer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerEntityDTOConverterTest {

    private final CustomerEntityDTOConverter customerEntityDTOConverter = new CustomerEntityDTOConverter();

    @Test
    public void convertEntity(){

        //given

        Customer customer = new Customer();
        customer.setCustomerId(3L);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        //when

        CustomerDTO expected = customerEntityDTOConverter.convertEntity(customer);

        //then

        assertThat(expected).isNotNull();
        assertThat(expected.getCustomerID()).isEqualTo(3L);
        assertThat(expected.getFirstName()).isEqualTo("John");
        assertThat(expected.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void convertAdModCustomerDTO(){

        //given
        AddModCustomerDTO addModCustomerDTO = new AddModCustomerDTO();
        addModCustomerDTO.setFirstName("Anna");
        addModCustomerDTO.setLastName("Dzik");

        //when
        Customer expected = customerEntityDTOConverter.convertAdModCustomerDTO(addModCustomerDTO);

        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getFirstName()).isEqualTo("Anna");
        assertThat(expected.getLastName()).isEqualTo("Dzik");



    }

}
