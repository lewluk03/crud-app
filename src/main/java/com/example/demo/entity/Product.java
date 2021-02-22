package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="product")
public class Product {

    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "SEQ_PRODUCT", allocationSize = 1)
    private Long productId;

    @Column(name="product_name")
    private String productName;

    @Column(name="product_type")
    private String productType;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

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
