package com.example.demo.dto.converter;

import com.example.demo.dto.BucketPositionDTO;
import com.example.demo.entity.BucketPosition;
import org.springframework.stereotype.Component;

@Component
public class BucketPositionEntityDTOConverter {

    public BucketPositionDTO convertEntity(BucketPosition bucketPosition) {
        return new BucketPositionDTO(bucketPosition.getProduct().getProductId(), bucketPosition.getProduct().getProductName(), bucketPosition.getQuantity());
    }
}
