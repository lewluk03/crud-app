package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddModProductDTOTest {

    @Test
    public void testAddModProductDTO(){

        AddModProductDTO addModProductDTO = new AddModProductDTO();
        addModProductDTO.setProductName("Sony QWE123");
        addModProductDTO.setProductType("TV");

        assertThat(addModProductDTO).isNotNull();
        assertThat(addModProductDTO.getProductName()).isEqualTo("Sony QWE123");
        assertThat(addModProductDTO.getProductType()).isEqualTo("TV");

    }
}
