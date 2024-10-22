package com.example.Library1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.Staff;
import com.example.Library1.entity.Students;

@Repository
public interface StudentsRepo extends JpaRepository<Students, Integer>{
	
	@Query("select s from Students s where s.name like:key")
	List<Students> searchByName(@Param("key") String name);
	
	Students findByEmail(String email);
}
