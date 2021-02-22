package com.example.demo.dto;

import java.util.List;

public class BucketDTO {

    private final Long bucketId;
    private final String customerName;
    private final List<BucketPositionDTO> positions;

    public BucketDTO(Long bucketId, String customerName, List<BucketPositionDTO> bucketPositionList) {
        this.bucketId = bucketId;
        this.customerName = customerName;
        this.positions = bucketPositionList;
    }

    public Long getBucketId() {
        return bucketId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<BucketPositionDTO> getPositions() {
        return positions;
    }
}
