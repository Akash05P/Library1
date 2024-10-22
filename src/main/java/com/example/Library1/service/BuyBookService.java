package com.example.Library1.service;

import com.example.Library1.entity.BuyBook;
import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.Students;
import com.example.Library1.repository.BuyBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuyBookService {

    @Autowired
    private BuyBookRepo buyBookRepo;

    // Method to record a book purchase
    public void buyBook(Students student, CatalogItem catalog) {
        Optional<BuyBook> existingPurchase = buyBookRepo.findByStudentAndCatalog(student, catalog);

        if (existingPurchase.isPresent()) {
            throw new RuntimeException("Book already purchased by this student");
        } else {
            BuyBook newPurchase = new BuyBook(null, student, catalog);
            buyBookRepo.save(newPurchase); // Save the new purchase record
        }
    }

    // Method to get all purchased books for a specific student
    public List<CatalogItem> getPurchasedBooksByStudent(Students student) {
        List<BuyBook> purchasedBooks = buyBookRepo.findByStudent(student);
        return purchasedBooks.stream()
                .map(BuyBook::getCatalog) // Map to get the CatalogItem from BuyBook
                .collect(Collectors.toList());
    }

    // Method to get the total number of books purchased by all students
    public int getTotalBooksPurchasedByAllStudents() {
        List<BuyBook> allPurchases = buyBookRepo.findAll(); // Fetch all book purchases
        return allPurchases.size(); // Return the total number of books purchased
    }
}
