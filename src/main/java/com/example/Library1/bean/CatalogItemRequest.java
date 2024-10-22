package com.example.Library1.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogItemRequest {
	
	 private int itemId;
	private String titleName;
	private String author;
	private String price;
	private String image;
	private String description;
	private int quantity;
	private String genre;
	


}
