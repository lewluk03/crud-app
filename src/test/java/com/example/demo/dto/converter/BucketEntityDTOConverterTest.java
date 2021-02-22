package com.example.demo.dto.converter;

import com.example.demo.dto.BucketDTO;
import com.example.demo.entity.Bucket;
import com.example.demo.entity.BucketPosition;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BucketEntityDTOConverterTest {


    private final BucketPositionEntityDTOConverter bucketPositionEntityDTOConverter = new BucketPositionEntityDTOConverter();
    private final BucketEntityDTOConverter bucketEntityDTOConverter = new BucketEntityDTOConverter(bucketPositionEntityDTOConverter);


    @Test
    public void convertEntity(){

        //given

        Customer customer = new Customer();
        customer.setCustomerId(3L);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Product product = new Product();
        product.setProductId(2L);
        product.setProductName("Xbox");

        BucketPosition bucketPosition = new BucketPosition();
        bucketPosition.setProduct(product);
        bucketPosition.setQuantity(4);
        Set<BucketPosition> bucketPositionSet = new HashSet<BucketPosition>();
        bucketPositionSet.add(bucketPosition);

        Bucket bucket = new Bucket();
        bucket.setBucketId(3L);
        bucket.setCustomer(customer);
        bucket.setBucketPosition(bucketPositionSet);

        //when

        BucketDTO expected = bucketEntityDTOConverter.convertEntity(bucket);

        //then

        assertThat(expected).isNotNull();
        assertThat(expected.getPositions()).isNotNull();
        assertThat(expected.getCustomerName()).isEqualTo("JohnDoe");
        assertThat(expected.getPositions().get(0).getProductName()).isEqualTo("Xbox");

    }
}
