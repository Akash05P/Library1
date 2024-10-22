package com.example.Library1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Library1.bean.ApiResponse;
import com.example.Library1.bean.StudentsRequset;
import com.example.Library1.bean.StudentsRequset;
import com.example.Library1.service.StudentsService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/students")

public class StudentsController {
	
	@Autowired
	StudentsService studentsService;
	
	@GetMapping("/hello")
	public String Hello() {
		return "hello world";
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<StudentsRequset> createstudent(@RequestBody StudentsRequset request){
		
		StudentsRequset createdRequest=  this.studentsService.createstudent(request);
		return new ResponseEntity<>(createdRequest,HttpStatus.CREATED);
	}
	
	@PutMapping("/update{id}")
	public ResponseEntity<StudentsRequset> upadteStudent(@RequestBody StudentsRequset request ,@PathVariable Integer id){
		
		StudentsRequset updatedStudent= this.studentsService.upadatestudent(request, id);
		return ResponseEntity.ok(updatedStudent);
	}
	
	@DeleteMapping("/delete{id}")
	public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Integer id ){
		 this.studentsService.deletestudent(id);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("Student deleted successfully",true),HttpStatus.OK);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<StudentsRequset>> getAllStudents(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue ="10",required = false)Integer pageSize){
		return ResponseEntity.ok(this.studentsService.getAllstudents(pageNumber,pageSize));
	}
	
	
	@GetMapping("/getStudent{id}")
	public ResponseEntity<StudentsRequset> getStudent(@PathVariable Integer id){
		return ResponseEntity.ok(this.studentsService.getstudentById(id));
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<StudentsRequset>> searchPatientByName(@PathVariable("keyword") String keywords){
		
		List<StudentsRequset> result= this.studentsService.searchstudent(keywords);
		return new ResponseEntity<List<StudentsRequset>>(result,HttpStatus.OK);
	}

}
