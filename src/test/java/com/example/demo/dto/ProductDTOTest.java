package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDTOTest {


    @Test
    public void testProductDTO(){

        ProductDTO expected = new ProductDTO(2L, "Nintendo", "Console");


        assertThat(expected).isNotNull();
        assertThat(expected.getProductId()).isEqualTo(2l);
        assertThat(expected.getProductName()).isEqualTo("Nintendo");
        assertThat(expected.getProductType()).isEqualTo("Console");
    }

}
