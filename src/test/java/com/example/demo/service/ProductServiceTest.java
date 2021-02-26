package com.example.demo.service;


import com.example.demo.dto.AddModProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> productIdArgumentCaptor;

    @Test
    public void getAllProduct(){

        //given

        given(productRepository.findAll()).willReturn(Arrays.asList(
                createProduct(2L, "Sony PS5", "Console"),
                createProduct(3L, "Xbox Series X", "Console")
        ));

        //when
        List<Product> expected = productService.findAll();

        //Then

        assertThat(expected).isNotNull();
        assertThat(expected)
                .hasSize(2)
                .extracting(p -> p.getProductName() + " "+ p.getProductType())
                .containsExactlyInAnyOrder("Xbox Series X Console", "Sony PS5 Console");


    }

    @Test
    public void getProduct(){

        //given
        Product product = createProduct(2L, "Sony PS5", "Console");

        given(productRepository.findById(2L)).willReturn(Optional.of(product));

        //when

        Product expected = productService.findById(2L);

        //then

        assertThat(expected).isNotNull();
        assertThat(expected.getProductName()).isEqualTo("Sony PS5");
        assertThat(expected.getProductType()).isEqualTo("Console");
    }

    @Test
    public void addProduct(){

        //given

        Product product = new Product();
        product.setProductName("Sony PS5");
        product.setProductType("Console");

        //when

        productService.addProduct(product);

        //then

        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
        Product expected = productArgumentCaptor.getValue();

        assertThat(expected).isNotNull();
        assertThat(expected.getProductName()).isEqualTo("Sony PS5");
        assertThat(expected.getProductType()).isEqualTo("Console");

    }

    @Test
    public void updateProduct(){

        //given

        AddModProductDTO newProduct = new AddModProductDTO();
        newProduct.setProductName("Sony XWE");
        newProduct.setProductType("PAD");

        Product product = createProduct(34L,"SonyX", "PA");

        given(productRepository.findById(34L)).willReturn(Optional.of(product));

        //when

        productService.updateProduct(34L, newProduct);

        //then
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
        Product expected = productArgumentCaptor.getValue();

        assertThat(expected).isNotNull();
        assertThat(expected.getProductName()).isEqualTo("Sony XWE");
        assertThat(expected.getProductType()).isEqualTo("PAD");


    }

    @Test
    public void deleteProduct(){

        //given

        Long productId = 32L;

        //when

        productService.deleteProduct(productId);

        //then
        verify(productRepository, times(1)).deleteById(productIdArgumentCaptor.capture());
        Long expected = productIdArgumentCaptor.getValue();

        assertThat(expected).isEqualTo(productId);

    }



    private Product createProduct(Long productId, String productName, String produckType){
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductType(produckType);

        return product;

    }

}
