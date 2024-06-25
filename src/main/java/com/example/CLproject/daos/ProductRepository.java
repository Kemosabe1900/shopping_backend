package com.example.CLproject.daos;

import com.example.CLproject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String name);

    List<Product> findByNameContainingIgnoreCase(String name);
}

