package com.example.Library1.service;

import java.util.List;

import com.example.Library1.bean.StudentsRequset;
import com.example.Library1.entity.CatalogItem;



public interface StudentsService {
	
	StudentsRequset createstudent(StudentsRequset request);
	StudentsRequset upadatestudent(StudentsRequset request,Integer studentId);
	StudentsRequset getstudentById(Integer studentId);
	List<StudentsRequset> getAllstudents(Integer pageNumber,Integer pageSize);
	void deletestudent(Integer studentId);
	List<StudentsRequset> searchstudent(String keyword);
	

}
