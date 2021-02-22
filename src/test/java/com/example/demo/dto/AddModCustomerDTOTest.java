package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddModCustomerDTOTest {

    @Test
    public void testAddModCustomerDTO(){

        AddModCustomerDTO addModCustomerDTO = new AddModCustomerDTO();
        addModCustomerDTO.setFirstName("Maria");
        addModCustomerDTO.setLastName("Kowalik");

        assertThat(addModCustomerDTO).isNotNull();
        assertThat(addModCustomerDTO.getFirstName()).isEqualTo("Maria");
        assertThat(addModCustomerDTO.getLastName()).isEqualTo("Kowalik");

    }
}
