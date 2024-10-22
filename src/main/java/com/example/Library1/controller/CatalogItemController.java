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
import com.example.Library1.bean.CatalogItemRequest;
import com.example.Library1.service.CatalogItemService;


@RestController
@CrossOrigin( "https://localhost:3000/")
@RequestMapping("/api/catalogItem")

public class CatalogItemController {
	
	@Autowired
	CatalogItemService catalogItemService;
	
	@GetMapping("/hello")
	public String Hello() {
		return "hello world";
	}
	
	@PostMapping("/create")
	public ResponseEntity<CatalogItemRequest> createcatalogitem(@RequestBody CatalogItemRequest request){
		
		CatalogItemRequest createdRequest=  this.catalogItemService.createcatalogitem(request);
		return new ResponseEntity<>(createdRequest,HttpStatus.CREATED);
	}
	
	@PutMapping("/update{id}")
	public ResponseEntity<CatalogItemRequest> upadteCatalogItem(@RequestBody CatalogItemRequest request ,@PathVariable Integer id){
		
		CatalogItemRequest updatedCatalogItem= this.catalogItemService.upadatecatalogitem(request, id);
		return ResponseEntity.ok(updatedCatalogItem);
	}
	
	@DeleteMapping("/delete{id}")
	public ResponseEntity<ApiResponse> deleteCatalogItem(@PathVariable Integer id ){
		 this.catalogItemService.deletecatalogitem(id);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("CatalogItem deleted successfully",true),HttpStatus.OK);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CatalogItemRequest>> getAllCatalogItems(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue ="20",required = false)Integer pageSize){
		return ResponseEntity.ok(this.catalogItemService.getAllcatalogitems(pageNumber,pageSize));
	}
	
	
	@GetMapping("/getCatalogItem{id}")
	public ResponseEntity<CatalogItemRequest> getCatalogItem(@PathVariable Integer id){
		return ResponseEntity.ok(this.catalogItemService.getcatalogitemById(id));
	}
	
    @GetMapping("/search")
    public ResponseEntity<List<CatalogItemRequest>> searchCatalogItems(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "price", required = false) String price) {

        List<CatalogItemRequest> result = this.catalogItemService.searchcatalogitem(keyword, price);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
