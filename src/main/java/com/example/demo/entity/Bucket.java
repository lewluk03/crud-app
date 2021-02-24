package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="bucket")
public class Bucket implements Serializable {

    private static final long serialVersionUID = 4142933944531707985L;

    @Id
    @Column(name="bucket_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bucket_seq")
    @SequenceGenerator(name = "bucket_seq", sequenceName = "SEQ_BUCKET", allocationSize = 1)
    private Long bucketId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL,orphanRemoval = true, fetch=FetchType.EAGER)
    private Set<BucketPosition> bucketPosition;

    public Long getBucketId() {
        return bucketId;
    }

    public void setBucketId(Long bucketId) {
        this.bucketId = bucketId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<BucketPosition> getBucketPosition() {
        return bucketPosition;
    }

    public void setBucketPosition(Set<BucketPosition> bucketPosition) {
        this.bucketPosition = bucketPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bucket bucket = (Bucket) o;

        if (!Objects.equals(bucketId, bucket.bucketId)) return false;
        return Objects.equals(customer, bucket.customer);
    }

    @Override
    public int hashCode() {
        int result = bucketId != null ? bucketId.hashCode() : 0;
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }
}
