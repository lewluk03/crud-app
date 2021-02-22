package com.example.demo.dto;

public class BucketPositionDTO {

    private final Long productId;
    private final String productName;
    private final int quantity;

    public BucketPositionDTO(Long productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
