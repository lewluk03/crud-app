package com.example.demo.dto.converter;

import com.example.demo.dto.BucketDTO;
import com.example.demo.entity.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BucketEntityDTOConverter {

    private final BucketPositionEntityDTOConverter bucketPositionEntityDTOConverter;


    public BucketEntityDTOConverter(BucketPositionEntityDTOConverter bucketPositionEntityDTOConverter) {
        this.bucketPositionEntityDTOConverter = bucketPositionEntityDTOConverter;
    }

    public BucketDTO convertEntity(Bucket bucket) {
        return new BucketDTO(bucket.getBucketId(),
                bucket.getCustomer().getFirstName() + bucket.getCustomer().getLastName(),
                bucket.getBucketPosition().stream().map(bucketPositionEntityDTOConverter::convertEntity).collect(Collectors.toList()));
    }
}
