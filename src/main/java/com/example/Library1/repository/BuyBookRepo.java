package com.example.Library1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Library1.entity.BuyBook;

import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.Students;

public interface BuyBookRepo extends JpaRepository<BuyBook, Integer>{

	Optional<BuyBook> findByStudentAndCatalog(Students student, CatalogItem catalog);
	   List<BuyBook> findByStudent(Students student);
}
