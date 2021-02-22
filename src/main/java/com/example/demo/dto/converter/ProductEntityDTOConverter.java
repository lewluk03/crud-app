package com.example.demo.dto.converter;


import com.example.demo.dto.AddModProductDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityDTOConverter {

    public ProductDTO convertEntity(Product product){
        return new ProductDTO(product.getProductId(), product.getProductName(), product.getProductType());
    }

    public Product convertAddModProductDTO(AddModProductDTO addProductDTO){
        Product product = new Product();
        product.setProductName(addProductDTO.getProductName());
        product.setProductType(addProductDTO.getProductType());
        return product;
    }
}
