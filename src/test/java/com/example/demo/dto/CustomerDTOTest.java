package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerDTOTest {

    @Test
    public void testCustomerDTO(){

            CustomerDTO customerDTO = new CustomerDTO(3L,"John", "Doe");
            assertThat(customerDTO).isNotNull();
            assertThat(customerDTO.getCustomerID()).isEqualTo(3L);
            assertThat(customerDTO.getFirstName()).isEqualTo("John");
            assertThat(customerDTO.getLastName()).isEqualTo("Doe");

    }
}
