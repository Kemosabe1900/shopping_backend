package com.example.CLproject.services;
import com.example.CLproject.daos.ProductRepository;
import com.example.CLproject.models.Product;
import com.example.CLproject.models.dtos.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import java.util.List;

@Service

public class ProductService {

    @Autowired

    private ProductRepository productDAO;

    public Product createProductService(ProductDTO productDTO){

        if(productDTO.getName().isBlank() || productDTO.getName() == null    ){
            throw new IllegalArgumentException("Product Name cannot be empty");
        }
        if(productDTO.getDescription().isBlank() || productDTO.getDescription() == null){
            throw new IllegalArgumentException("Product Name cannot be empty");
        }

        if(productDTO.getPrice() == null ){
            throw new IllegalArgumentException("Product Name cannot be empty");
        }

        Product newProduct = new Product( productDTO.getProducerId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getImage());
        productDAO.save(newProduct);
        return newProduct;
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();

    }

    public Product getProductById(Integer id) {
        return productDAO.findById(id).orElse(null);

    }

    public void deleteProductService(int id){
        Product product = productDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productDAO.deleteById(id);
    }

    public Product editProduct(int prodId, Product product){
        if(product.getName() == null || product.getName().isBlank()){
            throw new IllegalArgumentException("Product Name cannot be empty");
        }
        if(product.getDescription() == null || product.getDescription().isBlank()){
            throw new IllegalArgumentException("Product description cannot be empty");
        }

        Product editedP = productDAO.findById(prodId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if(editedP != null){
            editedP.setPrice(product.getPrice());
            editedP.setDescription(product.getDescription());
            productDAO.save(editedP);
        }
        return editedP;
    }

    public List<Product> viewProducts(String name){
        if (name.isBlank()) {
            throw new IllegalArgumentException("Product Name cannot be empty");
        }
        List<Product> products = productDAO.findByNameContainingIgnoreCase(name);
        if(products.isEmpty()){
            throw new IllegalArgumentException("No product found with name: " + name);
        }
        return products;

    }

    private ProductDTO convertToProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProducerId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        // productDTO.setImage(product.getImage()); // Commented out for now
        return productDTO;
    }
}