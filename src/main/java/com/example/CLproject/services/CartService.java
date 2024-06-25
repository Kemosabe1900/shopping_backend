package com.example.CLproject.services;
import com.example.CLproject.daos.CartItemRepository;
import com.example.CLproject.models.Cart;
import com.example.CLproject.models.CartItem;
import com.example.CLproject.models.Product;
import com.example.CLproject.models.User;
import com.example.CLproject.models.dtos.CartDTO;
import com.example.CLproject.models.dtos.ProductDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemDAO;



    public List<CartDTO> getCartItems() {

        return cartItemDAO.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    private CartDTO convertToDTO(CartItem cartItem){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cartItem.getId());
        cartDTO.setUser(cartItem.getUser());
        ProductDTO productDTO = convertToProductDTO(cartItem.getProduct());
        cartDTO.setProduct(productDTO);
        return cartDTO;
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


    public void addToCart(CartItem cartItem) {
        cartItemDAO.save(cartItem);
    }

    public void removeFromCart(Long id) {
        cartItemDAO.deleteById(id);
    }


    public CartDTO getCartItemsForUser(User user){
        Cart cart = user.getCart();

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUser(cart.getUser());
        cartDTO.setItems(cart.getItems());

        return cartDTO;
    }


    public void addItem(CartItem item, Cart cart) {

        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getProduct().getId() == item.getProduct().getId()) {
                return; // Item is already in the cart, so we don't add it again
            }
        }
        cart.getItems().add(item); // Item is not in the cart, so we add it
    }

    public void removeItem(Cart cart, CartItem item) {
        cart.getItems().remove(item);
    }

    public double calculateTotal(Cart cart) {
        double total = 0;
        for (CartItem item : cart.getItems()) {
            total += item.getProduct().getPrice();
        }
        return total;
    }


}