package com.example.demo.dto.converter;


import com.example.demo.dto.BucketPositionDTO;
import com.example.demo.entity.BucketPosition;
import com.example.demo.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BucketPositionEntityDTOConverterTest {

    private final BucketPositionEntityDTOConverter bucketPositionEntityDTOConverter = new BucketPositionEntityDTOConverter();

    @Test
    public void convertToDTO(){

        //given
        Product product = new Product();
        product.setProductId(2L);
        product.setProductName("Xbox");

        BucketPosition bucketPosition = new BucketPosition();
        bucketPosition.setProduct(product);
        bucketPosition.setQuantity(4);

        //when
        BucketPositionDTO expected = bucketPositionEntityDTOConverter.convertEntity(bucketPosition);

        //then

        assertThat(expected).isNotNull();
        assertThat(expected.getProductId()).isEqualTo(2l);
        assertThat(expected.getProductName()).isEqualTo("Xbox");
        assertThat(expected.getQuantity()).isEqualTo(4);

    }
}
