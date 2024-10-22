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
import com.example.Library1.bean.StaffRequest;
import com.example.Library1.service.StaffService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/staff")

public class StaffController {
	
	
	@Autowired
	StaffService staffService;

	@GetMapping("/hello")
	public String Hello() {
		return "hello world";
	}
	
	@PostMapping("/create")
	public ResponseEntity<StaffRequest> createstaff(@RequestBody StaffRequest request){
		
		StaffRequest createdRequest=  this.staffService.createstaff(request);
		return new ResponseEntity<>(createdRequest,HttpStatus.CREATED);
	}
	
	@PutMapping("/update{id}")
	public ResponseEntity<StaffRequest> upadteUser(@RequestBody StaffRequest request ,@PathVariable Integer id){
		
		StaffRequest updatedUser= this.staffService.upadatestaff(request, id);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/delete{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id ){
		 this.staffService.deletestaff(id);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<StaffRequest>> getAllUsers(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue ="10",required = false)Integer pageSize){
		return ResponseEntity.ok(this.staffService.getAllstaffs(pageNumber,pageSize));
	}
	
	
	@GetMapping("/getUser{id}")
	public ResponseEntity<StaffRequest> getUser(@PathVariable Integer id){
		return ResponseEntity.ok(this.staffService.getstaffById(id));
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<StaffRequest>> searchPatientByName(@PathVariable("keyword") String keywords){
		
		List<StaffRequest> result= this.staffService.searchStaff(keywords);
		return new ResponseEntity<List<StaffRequest>>(result,HttpStatus.OK);
	}

}
