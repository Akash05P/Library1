package com.example.Library1.service;

import java.util.List;

import com.example.Library1.bean.CatalogItemRequest;

public interface CatalogItemService {
	
	CatalogItemRequest createcatalogitem(CatalogItemRequest request);
	CatalogItemRequest upadatecatalogitem(CatalogItemRequest request,Integer catalogitemId);
	CatalogItemRequest getcatalogitemById(Integer catalogitemId);
	List<CatalogItemRequest> getAllcatalogitems(Integer pageNumber,Integer pageSize);
	void deletecatalogitem(Integer catalogitemId);
	List<CatalogItemRequest> searchcatalogitem(String keyword, String price);

}
