package com.example.demo.controller;

import com.example.demo.dto.AddModProductDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.converter.ProductEntityDTOConverter;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/product")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductEntityDTOConverter productEntityDTOConverter;

    @Autowired
    public ProductController(ProductService productService, ProductEntityDTOConverter productEntityDTOConverter){
        this.productService = productService;
        this.productEntityDTOConverter = productEntityDTOConverter;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(){

        return productService.findAll().stream().map(productEntityDTOConverter::convertEntity).collect(Collectors.toList());
    }

    @GetMapping("{productId}")
    public ProductDTO getProductByID(@PathVariable("productId") Long productId){
        return  productEntityDTOConverter.convertEntity(productService.findById(productId));
    }

    @DeleteMapping("{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
    }

    @PostMapping
    public void addProduct(@Valid @RequestBody AddModProductDTO product){
        productService.addProduct(productEntityDTOConverter.convertAddModProductDTO(product));
    }

    @PutMapping("{productId}")
    public void updateProduct(@PathVariable("productId") Long productId, @Valid @RequestBody AddModProductDTO newProduct){
            productService.updateProduct(productId, newProduct);

    }


}
