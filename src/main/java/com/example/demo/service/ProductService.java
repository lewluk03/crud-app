package com.example.demo.service;

import com.example.demo.dto.AddModProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.excetion.ResourceNotFoundException;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long productId){
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
    }

    @Transactional
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }

    @Transactional
    public void addProduct(Product product){
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long productId, AddModProductDTO newProduct){

        Product product = findById(productId);
        product.setProductName(newProduct.getProductName());
        product.setProductType(newProduct.getProductType());

        productRepository.save(product);

    }

}
