package com.example.Library1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Library1.entity.Cart;
import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.Students;
import com.example.Library1.repository.CartRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    // Method to save or update a cart item
    public void saveOrUpdateCart(Students student, CatalogItem catalog, boolean isCart) {
        Optional<Cart> existingCart = cartRepo.findByStudentAndCatalog(student, catalog);
        
        if (existingCart.isPresent()) {
            // If item is removed, delete the existing cart entry
            if (!isCart) {
                cartRepo.delete(existingCart.get()); // Remove the cart entry
            } else {
                Cart cart = existingCart.get();
                cart.setCart(isCart); // Update the cart status if needed
                cartRepo.save(cart); // Save the updated cart entry
            }
        } else {
            // Create a new entry if it does not exist
            Cart newCart = new Cart(null, student, catalog, isCart);
            cartRepo.save(newCart); // Save the new cart entry
        }
    }

    // Method to get all catalog items for a specific student
    public List<CatalogItem> getCartItemsByStudent(Students student) {
        List<Cart> cartItems = cartRepo.findByStudent(student);
        return cartItems.stream()
                .filter(Cart::isCart) // Filter for items that are in the cart
                .map(Cart::getCatalog) // Map to get the CatalogItem from Cart
                .collect(Collectors.toList());
    }
}
