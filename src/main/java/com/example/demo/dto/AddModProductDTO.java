package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

public class AddModProductDTO {

    @NotBlank
    private String productName;

    @NotBlank
    private String productType;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
