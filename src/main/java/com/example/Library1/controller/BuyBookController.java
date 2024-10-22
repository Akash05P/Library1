package com.example.Library1.controller;

import com.example.Library1.entity.BuyBook;
import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.Students;
import com.example.Library1.repository.StudentsRepo;
import com.example.Library1.service.BuyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyBook")
public class BuyBookController {

    @Autowired
    private BuyBookService buyBookService;

    @Autowired
    private StudentsRepo studentsRepo;

    // Method to save a book purchase
    @PostMapping
    public ResponseEntity<String> buyBook(@RequestParam Integer studentId, @RequestParam Integer catalogItemId) {
        Students student = studentsRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setItemId(catalogItemId);

        buyBookService.buyBook(student, catalogItem);

        return ResponseEntity.ok("Book purchase successful");
    }

    // Method to get all purchased books by a student
    @GetMapping
    public ResponseEntity<List<CatalogItem>> getPurchasedBooksByStudent(@RequestParam Integer studentId) {
        Students student = studentsRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<CatalogItem> purchasedBooks = buyBookService.getPurchasedBooksByStudent(student);

        return ResponseEntity.ok(purchasedBooks);
    }

    // New Method to get the total number of books purchased by all students
    @GetMapping("/totalBooksPurchased")
    public ResponseEntity<Integer> getTotalBooksPurchasedByAllStudents() {
        int totalBooksPurchased = buyBookService.getTotalBooksPurchasedByAllStudents();
        return ResponseEntity.ok(totalBooksPurchased);
    }
}

