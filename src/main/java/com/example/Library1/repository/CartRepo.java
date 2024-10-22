package com.example.Library1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Library1.entity.Cart;
import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.Students;

public interface CartRepo extends JpaRepository<Cart, Integer>{

	Optional<Cart> findByStudentAndCatalog(Students student, CatalogItem catalog);
	   List<Cart> findByStudent(Students student);
}
	