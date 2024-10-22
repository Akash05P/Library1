package com.example.Library1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Library1.entity.Staff;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Integer>{

	@Query("select s from Staff s where s.name like:key")
	List<Staff> searchByName(@Param("key") String name);
	
	Staff findByEmail(String email); 	
	}
