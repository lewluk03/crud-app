package com.example.demo.dto.converter;

import com.example.demo.dto.AddModProductDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductEntityDTOConverterTest {

    private final ProductEntityDTOConverter productEntityDTOConverter = new ProductEntityDTOConverter();


    @Test
    public void convertEntity(){

        //given
        Product product = new Product();
        product.setProductId(2L);
        product.setProductName("Xbox");
        product.setProductType("Konsola");

        //when

        ProductDTO expected = productEntityDTOConverter.convertEntity(product);

        //then

        assertThat(expected).isNotNull();
        assertThat(expected.getProductId()).isEqualTo(2l);
        assertThat(expected.getProductName()).isEqualTo("Xbox");
        assertThat(expected.getProductType()).isEqualTo("Konsola");

    }

    @Test
    public void convertAddModProductDTO(){

        //given
        AddModProductDTO addModProductDTO = new AddModProductDTO();
        addModProductDTO.setProductName("PS5");
        addModProductDTO.setProductType("Konsola");

        //when

        Product expected = productEntityDTOConverter.convertAddModProductDTO(addModProductDTO);

        //then

        assertThat(expected).isNotNull();
        assertThat(expected.getProductName()).isEqualTo("PS5");
        assertThat(expected.getProductType()).isEqualTo("Konsola");
    }


}
