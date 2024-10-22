package com.example.Library1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private Integer id;
	
	 @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "student_id")
	    private Students student;
	 
	 
	  @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "catalogItem_id")
	    private CatalogItem catalog;

	    private boolean isCart;
	      
	    public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Students getStudent() {
			return student;
		}

		public void setStudent(Students student) {
			this.student = student;
		}

		public CatalogItem getCatalog() {
			return catalog;
		}

		public void setCatalog(CatalogItem catalog) {
			this.catalog = catalog;
		}

		public boolean isCart() {
			return isCart;
		}

		public void setCart(boolean isCart) {
			this.isCart = isCart;
		}

		public Cart(Integer id, Students student, CatalogItem catalog, boolean isCart) {
			super();
			this.id = id;
			this.student = student;
			this.catalog = catalog;
			this.isCart = isCart;
		}

		public Cart() {
	    	
	    }

}
