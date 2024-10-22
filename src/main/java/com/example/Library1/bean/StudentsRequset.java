package com.example.Library1.bean;

import java.util.List;
import java.util.Set;

import com.example.Library1.entity.CatalogItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentsRequset {

	private Integer id;
	private String name;
	private String email;
	private String studentaddress;
	private long studentphone;
	private String 	password;
	private Set<CatalogItem> catalogItems;
	
	
	private Set<StudentRoleBean> studentRoles;
}
