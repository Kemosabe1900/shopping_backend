package com.example.CLproject.controllers;

import com.example.CLproject.models.Product;
import com.example.CLproject.models.dtos.ProductDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.CLproject.services.ProductService;
import java.util.List;



@RestController

@RequestMapping("/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        try{
            Product newProduct = productService.createProductService(productDTO);
            return ResponseEntity.status(201).body(newProduct.getName() + " has been added");
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();

    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);

    }

    @DeleteMapping("/deleteProduct/{prodId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer prodId) {
        try{
            productService.deleteProductService(prodId);
            return ResponseEntity.status(200).body("Product has been deleted");
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @PutMapping("/editProduct/{prodId}")
    public ResponseEntity<Object> editProduct(@RequestBody Product product, @PathVariable int prodId){
        try {
            return ResponseEntity.ok(productService.editProduct(prodId, product));
        }catch(Exception e){
            return ResponseEntity.status(400).body("Update item failed");
        }

    }

    @GetMapping("/viewProducts/{name}")
    public ResponseEntity<Object> viewProducts(@PathVariable String name){
        try {
            return ResponseEntity.ok(productService.viewProducts(name));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(400).body("View product field");
        }

    }
}