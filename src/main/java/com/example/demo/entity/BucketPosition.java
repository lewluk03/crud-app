package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "bucketpositions")
public class BucketPosition {

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
}
