package com.example.demo.dto;

public class ProductDTO {

    private final Long productId;
    private final String productName;
    private final String productType;

    public ProductDTO(Long productId, String productName, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductType() {
        return productType;
    }
}
