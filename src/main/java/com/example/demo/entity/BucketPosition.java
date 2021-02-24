package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bucketpositions")
public class BucketPosition implements Serializable {

    private static final long serialVersionUID = -1626182385132070240L;

    @Id
    @Column(name ="buckpos_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bucketpos_seq")
    @SequenceGenerator(name = "bucketpos_seq", sequenceName = "SEQ_BUCKETPOSITIONS", allocationSize = 1)
    private Long bucketPosId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="bucket_id", nullable=false)
    private Bucket bucket;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @Column
    private int quantity;

    public Long getBucketPosId() {
        return bucketPosId;
    }

    public void setBucketPosId(Long bucketPosId) {
        this.bucketPosId = bucketPosId;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BucketPosition that = (BucketPosition) o;

        if (quantity != that.quantity) return false;
        if (!Objects.equals(bucketPosId, that.bucketPosId)) return false;
        if (!Objects.equals(bucket, that.bucket)) return false;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        int result = bucketPosId != null ? bucketPosId.hashCode() : 0;
        result = 31 * result + (bucket != null ? bucket.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }
}
