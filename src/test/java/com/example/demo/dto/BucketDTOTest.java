package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BucketDTOTest {

    @Test
    public void testBucketDTO(){

        BucketPositionDTO bucketPositionDTO = new BucketPositionDTO(4L, "Sony qwe2334", 4);
        List<BucketPositionDTO> bucketPosition = new ArrayList<>();
        bucketPosition.add(bucketPositionDTO);

        BucketDTO bucketDTO = new BucketDTO(3L,"John Doe",bucketPosition);

        assertThat(bucketDTO).isNotNull();
        assertThat(bucketDTO.getBucketId()).isEqualTo(3L);
        assertThat(bucketDTO.getCustomerName()).isEqualTo("John Doe");
        assertThat(bucketDTO.getPositions().get(0).getQuantity()).isEqualTo(4);


    }
}
