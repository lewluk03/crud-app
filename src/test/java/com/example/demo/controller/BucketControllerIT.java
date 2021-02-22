package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.dto.AddPositionToBucketDTO;

import com.example.demo.entity.Bucket;
import com.example.demo.entity.BucketPosition;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.repository.BucketPositionRepository;
import com.example.demo.repository.BucketReposiroty;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.utils.JsonUtil;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public class BucketControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BucketReposiroty bucketReposiroty;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BucketPositionRepository bucketPositionRepository;

    @AfterEach
    public void cleanUp(){
        bucketReposiroty.deleteAll();
        bucketReposiroty.flush();
    }

    @Test
    public void getBucketData() throws Exception {


        Bucket bucket = new Bucket();
        bucket.setCustomer(addCustomer("John","Doe"));

        Bucket savedBucket = bucketReposiroty.saveAndFlush(bucket);

        BucketPosition bucketPosition = new BucketPosition();
        bucketPosition.setBucket(savedBucket);
        bucketPosition.setProduct(addProduct("Xbox"));
        bucketPosition.setQuantity(4);

        bucketPositionRepository.saveAndFlush(bucketPosition);

        mvc.perform(MockMvcRequestBuilders.get("/api/bucket/" + savedBucket.getBucketId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName", CoreMatchers.is("JohnDoe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.positions.[*].productName", CoreMatchers.hasItems("Xbox")));
        ;
    }

    @Test
    public void getAllBucketData() throws Exception {

        Bucket bucket = new Bucket();
        bucket.setCustomer(addCustomer("John", "Doe"));
        Bucket savedBucket = bucketReposiroty.saveAndFlush(bucket);

        BucketPosition bucketPosition = new BucketPosition();
        bucketPosition.setBucket(savedBucket);
        bucketPosition.setProduct(addProduct("Xbox"));
        bucketPosition.setQuantity(4);

        bucketPositionRepository.saveAndFlush(bucketPosition);

        bucket = new Bucket();
        bucket.setCustomer(addCustomer("Anna", "Anna"));
        savedBucket = bucketReposiroty.saveAndFlush(bucket);

        bucketPosition = new BucketPosition();
        bucketPosition.setBucket(savedBucket);
        bucketPosition.setProduct(addProduct("PS5"));
        bucketPosition.setQuantity(4);

        bucketPositionRepository.saveAndFlush(bucketPosition);


        mvc.perform(MockMvcRequestBuilders.get("/api/bucket")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].customerName", CoreMatchers.hasItems("AnnaAnna","JohnDoe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].positions.[*].productName", CoreMatchers.hasItems("PS5","Xbox")));
        ;
    }


    @Test
    @Transactional
    public void addBucketData() throws Exception {
        Customer customer = addCustomer("Marian", "Pazdzioch");

        mvc.perform(MockMvcRequestBuilders.post("/api/bucket/" +  customer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Bucket productAfterUpdate = bucketReposiroty.findAll().iterator().next();
        assertThat(productAfterUpdate).isNotNull();
        assertThat(productAfterUpdate.getCustomer().getFirstName()).isEqualTo("Marian");
        assertThat(productAfterUpdate.getCustomer().getLastName()).isEqualTo("Pazdzioch");
    }


    @Test
    public void removeBucketData() throws Exception {

        Bucket bucket = new Bucket();
        bucket.setCustomer(addCustomer("John", "Doe"));
        Bucket savedBucket = bucketReposiroty.saveAndFlush(bucket);

        mvc.perform(MockMvcRequestBuilders.delete("/api/bucket/" + savedBucket.getBucketId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    public void addBuckerPosition() throws Exception{
        Customer customer = addCustomer("Stefan", "Jarocki");

        Bucket bucket = new Bucket();
        bucket.setCustomer(addCustomer("John", "Doe"));
        Bucket savedBucket = bucketReposiroty.saveAndFlush(bucket);

        Product product = addProduct("Sony qwe23");

        AddPositionToBucketDTO addPositionToBucketDTO = new AddPositionToBucketDTO();
        addPositionToBucketDTO.setQuantity(8);

        mvc.perform(MockMvcRequestBuilders.post("/api/bucket/" +  bucket.getBucketId() + "/positions/" + product.getProductId())
                .content(JsonUtil.toJson(addPositionToBucketDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        BucketPosition bucketPositionAfterUpdate = bucketPositionRepository.findAll().iterator().next();

        assertThat(bucketPositionAfterUpdate).isNotNull();
        assertThat(bucketPositionAfterUpdate.getQuantity()).isEqualTo(8);
        assertThat(bucketPositionAfterUpdate.getProduct().getProductName()).isEqualTo("Sony qwe23");


    }

    @Test
    public void deleteBucketPositionById() throws Exception{

        Customer customer = addCustomer("Stefan", "Jarocki");

        Bucket bucket = new Bucket();
        bucket.setCustomer(addCustomer("John", "Doe"));
        Bucket savedBucket = bucketReposiroty.saveAndFlush(bucket);

        Product product = addProduct("Sony qwe23");

        BucketPosition bucketPosition = new BucketPosition();
        bucketPosition.setBucket(savedBucket);
        bucketPosition.setProduct(product);
        bucketPosition.setQuantity(4);

        bucketPositionRepository.saveAndFlush(bucketPosition);

        mvc.perform(MockMvcRequestBuilders.delete("/api/bucket/" +  bucket.getBucketId() + "/positions/" + product.getProductId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Bucket bucketAfterDeletePosition = bucketReposiroty.findAll().iterator().next();

        assertThat(bucketAfterDeletePosition).isNotNull();
        assertThat(bucketAfterDeletePosition.getCustomer().getFirstName()).isEqualTo("John");
        assertThat(bucketAfterDeletePosition.getBucketPosition()).isEmpty();
    }


    public Customer addCustomer(String firstName, String lastName){

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        Customer saveCustomer = customerRepository.saveAndFlush(customer);

        return saveCustomer;

    }

    public Product addProduct(String productName){

        Product product = new Product();
        product.setProductName(productName);

        Product saveProduct = productRepository.saveAndFlush(product);

        return saveProduct;

    }
}
