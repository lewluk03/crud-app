package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BucketPositionDTOTest {

    @Test
    public void testBucketPositionDTO(){

        BucketPositionDTO bucketPositionDTO = new BucketPositionDTO(3L,"Sony QWE23", 6);

        assertThat(bucketPositionDTO).isNotNull();
        assertThat(bucketPositionDTO.getProductId()).isEqualTo(3L);
        assertThat(bucketPositionDTO.getProductName()).isEqualTo("Sony QWE23");
        assertThat(bucketPositionDTO.getQuantity()).isEqualTo(6);


    }
}
