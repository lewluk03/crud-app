package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="product")
public class Product implements Serializable {


    private static final long serialVersionUID = -5801992119021989182L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!Objects.equals(productId, product.productId)) return false;
        if (!Objects.equals(productName, product.productName)) return false;
        return Objects.equals(productType, product.productType);
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (productType != null ? productType.hashCode() : 0);
        return result;
    }
}
