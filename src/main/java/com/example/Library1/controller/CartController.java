package com.example.Library1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.Students;
import com.example.Library1.repository.StudentsRepo;
import com.example.Library1.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private StudentsRepo studentsRepo;

    // Method to save or update cart items
    @PostMapping
    public ResponseEntity<String> saveOrUpdateCart(@RequestParam Integer id, @RequestParam Integer itemId, @RequestParam boolean isCart) {
        Students student = studentsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        CatalogItem catalog = new CatalogItem();
        catalog.setItemId(itemId);

        cartService.saveOrUpdateCart(student, catalog, isCart);

        String message = isCart ? "Added to Cart" : "Removed from Cart";
        return ResponseEntity.ok(message);
    }

    // Method to get all catalog items in the cart for a specific student
    @GetMapping
    public ResponseEntity<List<CatalogItem>> getCartItemsByStudent(@RequestParam Integer id) {
        Students student = studentsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<CatalogItem> cartItems = cartService.getCartItemsByStudent(student);

        return ResponseEntity.ok(cartItems);
    }
}
