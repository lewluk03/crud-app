package com.example.demo.service;

import com.example.demo.entity.Bucket;
import com.example.demo.entity.BucketPosition;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.repository.BucketPositionRepository;
import com.example.demo.repository.BucketReposiroty;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BucketServiceTest {

    @InjectMocks
    private BucketService bucketService;

    @Mock
    private BucketReposiroty bucketReposiroty;

    @Mock
    private BucketPositionRepository bucketPositionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Captor
    private ArgumentCaptor<Bucket> bucketArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> bucketIdArgumentCaptor;

    @Captor ArgumentCaptor<BucketPosition> bucketPositionArgumentCaptor;

    @Test
    public void getAllBuckets(){

        //given
        given(bucketReposiroty.findAll()).willReturn(Arrays.asList(
                createBucket(2L, createCustomer(1L, "Jan", "Kowalski")),
                createBucket(3L, createCustomer(2L, "John", "Doe"))
        ));

        //when

        List<Bucket> expected = bucketService.findAll();

        //then
        assertThat(expected).isNotNull();
        assertThat(expected).hasSize(2)
                .extracting(b -> b.getBucketId() +" " + b.getCustomer().getLastName())
                .containsExactlyInAnyOrder("2 Kowalski", "3 Doe");

    }

    @Test
    public void getBucketById(){
        //given
        Bucket bucket =  createBucket(2L, createCustomer(1L, "Jan", "Kowalski"));

        given(bucketReposiroty.findById(2L)).willReturn(Optional.of(bucket));

        //when

        Bucket expected = bucketService.findById(2L);

        assertThat(expected).isNotNull();
        assertThat(expected).isEqualTo(bucket);


    }

    @Test
    public void addBucket(){
        //given
        Customer customer = createCustomer(2L, "John", "Doe");
        given(customerRepository.findById(2L)).willReturn(Optional.of(customer));

        //when
        bucketService.addBucket(2L);

        //Then
        verify(bucketReposiroty, times(1)).save(bucketArgumentCaptor.capture());
        Bucket expected = bucketArgumentCaptor.getValue();

        assertThat(expected).isNotNull();
        assertThat(expected.getCustomer().getLastName()).isEqualTo("Doe");
        assertThat(expected.getCustomer().getFirstName()).isEqualTo("John");

    }

    @Test
    public void deleteBucket(){

        //given
        Long bucketId = 3L;

        //when
        bucketService.deleteBucket(3L);

        verify(bucketReposiroty, times(1)).deleteById(bucketIdArgumentCaptor.capture());
        Long expected = bucketIdArgumentCaptor.getValue();

        //then
        assertThat(expected).isEqualTo(bucketId);
    }

    //Long bucketId, Long productId, int quantity
    @Test
    public void addBucketPosition(){
        //given
        Bucket bucket =  createBucket(2L, createCustomer(1L, "Jan", "Kowalski"));
        Product product = createProduct(2L, "Sony PS5","Console");

        given(bucketReposiroty.findById(2L)).willReturn(Optional.of(bucket));
        given(productRepository.findById(2L)).willReturn(Optional.of(product));


        //when
        bucketService.addBucketPosition(2L, 2L, 10);

        verify(bucketPositionRepository, times(1)).save(bucketPositionArgumentCaptor.capture());
        BucketPosition expected = bucketPositionArgumentCaptor.getValue();

        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getQuantity()).isEqualTo(10);

    }

    @Test
    public void deleteBucketPosition(){
        //given

        Bucket bucket =  createBucket(2L, createCustomer(1L, "Jan", "Kowalski"));
        BucketPosition bucketPositionOne = createBucketPosition(1L,
                bucket,
                createProduct(1L, "Sony", "Console"),
                10);
        BucketPosition bucketPositionTwo = createBucketPosition(2L,
                bucket,
                createProduct(2L, "Xbox", "Console"),
                20);

        Set<BucketPosition> bucketPositionSet = new HashSet<>();
        bucketPositionSet.add(bucketPositionOne);
        bucketPositionSet.add(bucketPositionTwo);
        bucket.setBucketPosition(bucketPositionSet);
        given(bucketReposiroty.findById(2L)).willReturn(Optional.of(bucket));

        bucketService.deleteBucketPosition(2L,2L);
        assertThat(bucket.getBucketPosition())
                .hasSize(1)
                .extracting(p -> p.getProduct().getProductName() + " " + p.getQuantity())
                .containsExactlyInAnyOrder("Sony 10");


    }


    private Customer createCustomer(Long id, String firstName, String lastName){
        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

    private Bucket createBucket(Long bucketId, Customer customer){
        Bucket bucket = new Bucket();
        bucket.setBucketId(bucketId);
        bucket.setCustomer(customer);
        return  bucket;
    }

    private Product createProduct(Long productId, String productName, String productType){
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductType(productType);
        return product;
    }

    private BucketPosition createBucketPosition(Long bucketPositionId, Bucket bucket, Product product, int quantity){
        BucketPosition bucketPosition = new BucketPosition();
        bucketPosition.setBucketPosId(bucketPositionId);
        bucketPosition.setBucket(bucket);
        bucketPosition.setProduct(product);
        bucketPosition.setQuantity(quantity);
        return bucketPosition;
    }
}
