package com.example.CLproject.models.dtos;

import com.example.CLproject.models.CartItem;
import com.example.CLproject.models.User;

import java.util.List;

public class CartDTO {

    private int id;
    private User user;
    private List<CartItem> items;
    private ProductDTO product;

    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }


    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}