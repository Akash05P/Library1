package com.example.Library1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.Staff;

@Repository
public interface CatalogItemRepo extends JpaRepository<CatalogItem, Integer>{
	
	   @Query("select s from CatalogItem s where s.titleName like :key or s.author like :key or s.price = :price")
	    List<CatalogItem> searchByNameOrAuthorOrPrice(@Param("key") String keyword, @Param("price") String price);

}
