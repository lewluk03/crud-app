package com.example.demo.service;

import com.example.demo.entity.Bucket;
import com.example.demo.entity.BucketPosition;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.excetion.ResourceNotFoundException;
import com.example.demo.repository.BucketPositionRepository;
import com.example.demo.repository.BucketReposiroty;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BucketService {

    private BucketReposiroty bucketReposiroty;
    private CustomerRepository customerRepository;
    private BucketPositionRepository bucketPositionRepository;
    private ProductRepository productRepository;

    @Autowired
    public BucketService(BucketReposiroty bucketReposiroty,
                         CustomerRepository customerRepository,
                         BucketPositionRepository bucketPositionRepository,
                         ProductRepository productRepository){
        this.bucketReposiroty = bucketReposiroty;
        this.customerRepository = customerRepository;
        this.bucketPositionRepository = bucketPositionRepository;
        this.productRepository = productRepository;
    }

    public List<Bucket> findAll(){
        return bucketReposiroty.findAll();
    }

    public Bucket findById(Long bucketId){
        return bucketReposiroty.findById(bucketId).orElseThrow(()-> new ResourceNotFoundException("Bucket", "bucketId", bucketId));
    }

    @Transactional
    public void addBucket(Long customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer", "customerId", customerId));
        Bucket bucket = new Bucket();
        bucket.setCustomer(customer);

        bucketReposiroty.save(bucket);

    }

    @Transactional
    public void deletebucket(Long bucketId){
        bucketReposiroty.deleteById(bucketId);
    }

    @Transactional
    public void addBucketPosition(Long bucketId, Long productId, int quantity){
        Bucket bucket = findById(bucketId);
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        BucketPosition bucketPosition = new BucketPosition();
        bucketPosition.setBucket(bucket);
        bucketPosition.setProduct(product);
        bucketPosition.setQuantity(quantity);

        bucketPositionRepository.save(bucketPosition);
    }

    @Transactional
    public void deleteBucketPosition(Long bucketId, Long productId){

        Bucket bucket = findById(bucketId);
        bucket.getBucketPosition().removeIf(p ->p.getProduct().getProductId().equals(productId));
    }
}
