package com.example.CLproject.daos;
import com.example.CLproject.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}