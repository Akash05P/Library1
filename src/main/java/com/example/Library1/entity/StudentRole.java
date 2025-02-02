package com.example.Library1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StudentRole {
			
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		private String name;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="student_id", nullable = false)
		private Students student;
		
	
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Students getStudent() {
			return student;
		}
		public void setStudent(Students student) {
			this.student = student;
		}
		
		public StudentRole(int id, String name, Students student) {
			super();
			this.id = id;
			this.name = name;
			this.student = student;
		}
		
		public StudentRole() {
			
		}
}
