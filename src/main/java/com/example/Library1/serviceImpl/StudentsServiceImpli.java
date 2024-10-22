package com.example.Library1.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Library1.bean.CatalogItemRequest;
import com.example.Library1.bean.StaffRequest;
import com.example.Library1.bean.StudentsRequset;
import com.example.Library1.entity.CatalogItem;
import com.example.Library1.entity.Staff;
import com.example.Library1.entity.StaffRole;
import com.example.Library1.entity.StudentRole;
import com.example.Library1.entity.Students;
import com.example.Library1.exception.ResourceNotFound;
import com.example.Library1.repository.CatalogItemRepo;
import com.example.Library1.repository.StaffRoleRepo;
import com.example.Library1.repository.StudentRoleRepo;
import com.example.Library1.repository.StudentsRepo;
import com.example.Library1.service.StudentsService;

@Service
public class StudentsServiceImpli implements StudentsService{
	
	@Autowired
	StudentsRepo studentsRepo;
	
	 @Autowired
	 private StudentRoleRepo studentRepo;
	
	@Autowired
	CatalogItemRepo catalogItemRepo;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	
//	@Override
//	public StudentsRequset createstudent(StudentsRequset request) {
//	    // Map the request object to an entity object using ModelMapper
//	    Students student = this.requestToEntity(request);
//	    student.setPassword(passwordEncoder.encode(request.getPassword()));
//	    
//	    // Initialize a set to hold the CatalogItem entities
//	    Set<CatalogItem> catalogItems = new HashSet<>();
//	    
//	    // Iterate over the CatalogItemRequest objects in the request
//	    for (CatalogItem catalogItemRequest : request.getCatalogItems()) {
//	        // Retrieve the CatalogItem entity from repository by itemId
//	        CatalogItem catalogItem = catalogItemRepo.findById(catalogItemRequest.getItemId())
//	                .orElseThrow(() -> new ResourceNotFound("CatalogItem", "Id", catalogItemRequest.getItemId()));
//	        catalogItems.add(catalogItem);
//	    }
//	    
//	    // Set the catalogItems association in the student entity
//	    student.setCatalogItems(catalogItems);
//	    
//	    // Save the student entity to the database
//	    Students savedStudent = this.studentsRepo.save(student);
//	    
//	    // Map the saved entity back to a request object and return it
//	    return this.entityToRequest(savedStudent);
//	}

	@Override
	public StudentsRequset createstudent(StudentsRequset studentRequest) {
	    // Convert StudentRequest to Student entity
	    Students student = this.requestToEntity(studentRequest);

	    // Set the password
	    student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));

	    // Save the Student entity
	    Students savedStudent = this.studentsRepo.save(student);

	    // Create the default role
	    StudentRole defaultRole = new StudentRole();
	    defaultRole.setName("student"); // Or set a relevant default role name
	    defaultRole.setStudent(savedStudent);

	    // Save the StudentRole entity
	    StudentRole savedRole = this.studentRepo.save(defaultRole);

	    // Update the student with the saved role
	    Set<StudentRole> roles = new HashSet<>();
	    roles.add(savedRole);
	    savedStudent.setStudentRoles(roles);

	    // Save the updated Student entity
	    this.studentsRepo.save(savedStudent);

	    // Convert the updated Student entity to StudentRequest
	    return this.entityToRequest(savedStudent);
	}


	
	
	@Override
	public StudentsRequset upadatestudent(StudentsRequset request, Integer studentId) {
	    // Retrieve the student entity from the repository
	    Students student = this.studentsRepo.findById(studentId)
	            .orElseThrow(() -> new ResourceNotFound("Students", "Id", studentId));
	    
	    // Update the fields of the retrieved entity with the values from the request
	    student.setName(request.getName());
	    student.setEmail(request.getEmail());
	    student.setStudentaddress(request.getStudentaddress());
	    student.setStudentphone(request.getStudentphone());
	    
	    // Fetch the CatalogItems associated with the request and update them
	    List<Integer> catalogItemIds = request.getCatalogItems().stream()
	            .map(CatalogItem::getItemId)
	            .collect(Collectors.toList());
	    List<CatalogItem> catalogItems = catalogItemRepo.findAllById(catalogItemIds);
	    student.setCatalogItems(new HashSet<>(catalogItems));
	    
	    // Save the updated student entity back to the database
	    Students updatedStudent = this.studentsRepo.save(student);
	    
	    // Map the updated entity back to a request object and return it
	    return this.entityToRequest(updatedStudent);
	}



	@Override
	public StudentsRequset getstudentById(Integer studentId) {
		
		 Students student = this.studentsRepo.findById(studentId)
	                .orElseThrow(() -> new ResourceNotFound("Students", "Id", studentId));
	        return this.entityToRequest(student);
		
	}

	@Override
	public List<StudentsRequset> getAllstudents(Integer pageNumber, Integer pageSize) {
		
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Students> pagepost= this.studentsRepo.findAll(p);
		List<Students> students=pagepost.getContent();
		
		
		List<StudentsRequset>request1= students.stream().map(student->this.entityToRequest(student)).collect(Collectors.toList());
		
		return request1;
	}

	@Override
	public void deletestudent(Integer studentId) {
		
		Students student=this.studentsRepo.findById(studentId).orElseThrow(()-> new ResourceNotFound("Students","Id",studentId));
		this.studentsRepo.delete(student);
		
	}

	@Override
	public List<StudentsRequset> searchstudent(String keyword) {
		
		List<Students> Studentss= this.studentsRepo.searchByName("%"+keyword+"%");
		List<StudentsRequset> StudentsRequests= Studentss.stream().map((Students)->this.modelMapper.map(Students, StudentsRequset.class)).collect(Collectors.toList());
		return StudentsRequests;
	}
	
	private Students requestToEntity(StudentsRequset request) {
		Students student= this.modelMapper.map(request, Students.class);
		
		
		return student;
	}
	
	private StudentsRequset entityToRequest(Students student) {
		StudentsRequset request=this.modelMapper.map(student, StudentsRequset.class);
		
		return request;
		
	}

	
	
}
