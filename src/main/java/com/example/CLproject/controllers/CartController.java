package com.example.CLproject.controllers;

import com.example.CLproject.models.Cart;
import com.example.CLproject.models.CartItem;
import com.example.CLproject.models.Product;
import com.example.CLproject.models.User;
import com.example.CLproject.models.dtos.CartDTO;
import com.example.CLproject.models.dtos.CartItemDTO;
import com.example.CLproject.services.ProductService;
import com.example.CLproject.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.CLproject.services.CartService;

import java.util.List;

@RestController

@RequestMapping("/cart")

public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;


    @GetMapping
    public List<CartDTO> getCartItems() {
        return cartService.getCartItems();

    }

    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody CartItemDTO cartItemDTO){
        try{
            Product product = productService.getProductById(cartItemDTO.getProductId());
            User user = userService.getUserById(cartItemDTO.getUserId());
            Cart cart = user.getCart();

            int quantity = cartItemDTO.getQuantity();
            CartItem cartItem = new CartItem(0, product,user,quantity);
            cartItem.setProduct(product);
            cartService.addToCart(cartItem, cart);

            return ResponseEntity.status(201).body("Product has been added to cart");
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
    }

}