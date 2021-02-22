package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddPositionToBucketDTOTest {

    @Test
    public void testAddPositionToBucket(){

        AddPositionToBucketDTO addPositionToBucketDTO = new AddPositionToBucketDTO();
        addPositionToBucketDTO.setQuantity(4);

        assertThat(addPositionToBucketDTO).isNotNull();
        assertThat(addPositionToBucketDTO.getQuantity()).isEqualTo(4);

    }
}
