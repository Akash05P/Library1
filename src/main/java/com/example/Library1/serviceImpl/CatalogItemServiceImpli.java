package com.example.Library1.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.example.Library1.bean.CatalogItemRequest;
import com.example.Library1.bean.CatalogItemRequest;
import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.CatalogItem;
import com.example.Library1.exception.ResourceNotFound;
import com.example.Library1.repository.CatalogItemRepo;
import com.example.Library1.service.CatalogItemService;

import jakarta.transaction.Transactional;


@Service
public class CatalogItemServiceImpli implements CatalogItemService{
	
	@Autowired
	CatalogItemRepo catalogItemRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CatalogItemRequest createcatalogitem(CatalogItemRequest request) {
		CatalogItem catalogItem=this.requestToEntity(request);
		CatalogItem savecatalogItem=this.catalogItemRepo.save(catalogItem);
		return this.entityToRequest(savecatalogItem);
	}

	@Override
	public CatalogItemRequest upadatecatalogitem(CatalogItemRequest request, Integer itemId) {
		
		CatalogItem catalogItem=this.catalogItemRepo.findById(itemId).orElseThrow(()-> new ResourceNotFound("CatalogItem","Id",itemId));
		
		catalogItem.setTitleName(request.getTitleName());
		catalogItem.setAuthor(request.getAuthor());
		catalogItem.setQuantity(request.getQuantity());
		catalogItem.setPrice(request.getPrice());
		catalogItem.setImage(request.getImage());
		catalogItem.setDescription(request.getDescription());
//		catalogItem.setGenre(request.getGenre());
		
		CatalogItem updatedcatalogItem=this.catalogItemRepo.save(catalogItem);
		CatalogItemRequest request1=this.entityToRequest(updatedcatalogItem);

		
		return request1;
	}

	
	@Override
	public CatalogItemRequest getcatalogitemById(Integer itemId) {
		
		CatalogItem catalogItem=this.catalogItemRepo.findById(itemId).orElseThrow(()-> new ResourceNotFound("CatalogItem","Id",itemId));
		return this.entityToRequest(catalogItem);
	}

	@Override
	public List<CatalogItemRequest> getAllcatalogitems(Integer pageNumber, Integer pageSize) {
		
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<CatalogItem> pagepost= this.catalogItemRepo.findAll(p);
		List<CatalogItem> catalogItems=pagepost.getContent();
			
		
		List<CatalogItemRequest>request1= catalogItems.stream().map(catalogItem->this.entityToRequest(catalogItem)).collect(Collectors.toList());
		
		return request1;
	}

	@Override
	@Modifying
	@Transactional
	public void deletecatalogitem(Integer itemId) {
		
		CatalogItem catalogItem=this.catalogItemRepo.findById(itemId).orElseThrow(()-> new ResourceNotFound("CatalogItem","Id",itemId));
		this.catalogItemRepo.delete(catalogItem);
		
	}

	@Override
	public List<CatalogItemRequest> searchcatalogitem(String keyword, String price) {
		
		
		List<CatalogItem> CatalogItems= this.catalogItemRepo.searchByNameOrAuthorOrPrice("%"+keyword+"%",price);
		List<CatalogItemRequest> CatalogItemRequests= CatalogItems.stream().map((CatalogItem)->this.modelMapper.map(CatalogItem, CatalogItemRequest.class)).collect(Collectors.toList());
		return CatalogItemRequests;
	}
	
	private CatalogItem requestToEntity(CatalogItemRequest request) {
		CatalogItem catalogItem= this.modelMapper.map(request, CatalogItem.class);
		
		
		return catalogItem;
	}
	
	private CatalogItemRequest entityToRequest(CatalogItem catalogItem) {
		CatalogItemRequest request=this.modelMapper.map(catalogItem, CatalogItemRequest.class);
		
		return request;
		
	}

	
	
	
	

}
