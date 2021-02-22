package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.utils.JsonUtil;
import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
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

import static com.example.demo.utils.SecurityUtils.httpBasicForTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void cleanUp(){
        productRepository.deleteAll();
        productRepository.flush();
    }

    @Test
    public void getProductData() throws Exception {
        Product product = new Product();
        product.setProductType("DVD");
        product.setProductName("Sony DVD p323 Player");
        Product savedProduct = productRepository.saveAndFlush(product);

        mvc.perform(MockMvcRequestBuilders.get("/api/product/" + savedProduct.getProductId())
                .with(httpBasicForTest())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is("Sony DVD p323 Player")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productType", CoreMatchers.is("DVD")))
        ;
    }

    @Test
    public void productNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/product/1")
                .with(httpBasicForTest())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void removeProductData() throws Exception {

        Product product = new Product();
        product.setProductType("DVD");
        product.setProductName("Sony DVD p323 Player");
        Product savedProduct = productRepository.saveAndFlush(product);

        mvc.perform(MockMvcRequestBuilders.delete("/api/product/" + savedProduct.getProductId())
                .with(httpBasicForTest())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }



    @Test
    @Transactional
    public void updateProductData() throws Exception {
        Product product = new Product();
        product.setProductType("DVD");
        product.setProductName("Sony DVD p323 Player");
        Product savedProduct = productRepository.saveAndFlush(product);


        Product updatedProduct = new Product();
        updatedProduct.setProductType("DVD");
        updatedProduct.setProductName("Sony DVD p323 Player UPDATED");

        mvc.perform(MockMvcRequestBuilders.put("/api/product/" + savedProduct.getProductId())
                .with(httpBasicForTest())
                .content(JsonUtil.toJson(updatedProduct))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Product productAfterUpdate = productRepository.getOne(product.getProductId());
        assertThat(productAfterUpdate.getProductName()).isEqualTo("Sony DVD p323 Player UPDATED");
    }


    @Test
    public void getAllProductData() throws Exception {
        Product product = new Product();
        product.setProductType("DVD");
        product.setProductName("Sony DVD p323 Player");
        productRepository.saveAndFlush(product);

        product = new Product();
        product.setProductType("Konsola");
        product.setProductName("PS5");
        productRepository.saveAndFlush(product);

        mvc.perform(MockMvcRequestBuilders.get("/api/product")
                .with(httpBasicForTest())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].productName", CoreMatchers.hasItems("PS5", "Sony DVD p323 Player")))
        ;
    }

    @Test
    @Transactional
    public void addProductData() throws Exception {
        Product product = new Product();
        product.setProductType("DVD");
        product.setProductName("Sony DVD p323 Player");

        mvc.perform(MockMvcRequestBuilders.post("/api/product/")
                .with(httpBasicForTest())
                .content(JsonUtil.toJson(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Product productAfterUpdate = productRepository.findAll().iterator().next();
        assertThat(productAfterUpdate.getProductName()).isEqualTo("Sony DVD p323 Player");
    }

}
