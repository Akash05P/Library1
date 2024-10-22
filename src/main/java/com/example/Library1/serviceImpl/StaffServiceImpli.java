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

import com.example.Library1.bean.StaffRequest;
import com.example.Library1.entity.Staff;
import com.example.Library1.entity.StaffRole;
import com.example.Library1.exception.ResourceNotFound;
import com.example.Library1.repository.StaffRepo;
import com.example.Library1.repository.StaffRoleRepo;
import com.example.Library1.service.StaffService;


@Service
public class StaffServiceImpli implements StaffService {
	
	@Autowired
	StaffRepo staffRepo;
	
	 @Autowired
	    private StaffRoleRepo staffRoleRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	public PasswordEncoder passwordEncoder;

//	@Override
//	public StaffRequest createstaff(StaffRequest staffRequest) {
//		
//		Staff staff=this.requestToEntity(staffRequest);
//		staff.setPassword(staffRequest.getPassword());
//		Staff savestaff=this.staffRepo.save(staff);
//		
//		StaffRole defaultRole=new StaffRole();
//		defaultRole.setName("Admin");
//		defaultRole.setStaff(staff);
//		
//		Set<StaffRole> roles=new HashSet<StaffRole>();
//		roles.add(defaultRole);
//		staff.setRoles(roles);
//		
//		return this.entityToRequest(savestaff);
//	}
	
	@Override
    public StaffRequest createstaff(StaffRequest staffRequest) {
        // Convert StaffRequest to Staff entity
        Staff staff = this.requestToEntity(staffRequest);

        // Set the password
	    staff.setPassword(passwordEncoder.encode(staffRequest.getPassword()));

        // Save the Staff entity
        Staff savedStaff = this.staffRepo.save(staff);

        // Create the default role
        StaffRole defaultRole = new StaffRole();
        defaultRole.setName("Admin");
        defaultRole.setStaff(savedStaff);

        // Save the StaffRole entity
        StaffRole savedRole = this.staffRoleRepo.save(defaultRole);

        // Update the staff with the saved role
        Set<StaffRole> roles = new HashSet<>();
        roles.add(savedRole);
        savedStaff.setRoles(roles);

        // Save the updated Staff entity
        this.staffRepo.save(savedStaff);

        // Convert the updated Staff entity to StaffRequest
        return this.entityToRequest(savedStaff);
    }

    // Other methods and implementations...


	
	

	@Override
	public StaffRequest upadatestaff(StaffRequest request, Integer staffId) {
		
		Staff staff=this.staffRepo.findById(staffId).orElseThrow(()-> new ResourceNotFound("Staff","Id",staffId));
		
		staff.setName(request.getName());
		staff.setEmail(request.getEmail());
		staff.setPassword(request.getPassword());
		staff.setPhone(request.getPhone());
		staff.setAddress(request.getAddress());
		
		Staff updatedstaff=this.staffRepo.save(staff);
		StaffRequest request1=this.entityToRequest(updatedstaff);

		
		return request1;
	}

	
	
	@Override
	public StaffRequest getstaffById(Integer staffId) {
		
		Staff staff=this.staffRepo.findById(staffId).orElseThrow(()-> new ResourceNotFound("Staff","Id",staffId));
		return this.entityToRequest(staff);
	}

	@Override
	public List<StaffRequest> getAllstaffs(Integer pageNumber,Integer pageSize) {
		
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Staff> pagepost= this.staffRepo.findAll(p);
		List<Staff> staffs=pagepost.getContent();
		
		
		List<StaffRequest>request1= staffs.stream().map(staff->this.entityToRequest(staff)).collect(Collectors.toList());
		
		return request1;
	}

	@Override
	public void deletestaff(Integer staffId) {
		Staff staff=this.staffRepo.findById(staffId).orElseThrow(()-> new ResourceNotFound("Staff","Id",staffId));
		this.staffRepo.delete(staff);
		
	}

	
	private Staff requestToEntity(StaffRequest request) {
		Staff staff= this.modelMapper.map(request, Staff.class);
		
		
		return staff;
	}
	
	private StaffRequest entityToRequest(Staff staff) {
		StaffRequest request=this.modelMapper.map(staff, StaffRequest.class);
		
		return request;
		
	}



	@Override
	public List<StaffRequest> searchStaff(String keyword) {
		List<Staff> Staffs= this.staffRepo.searchByName("%"+keyword+"%");
		List<StaffRequest> StaffRequests= Staffs.stream().map((Staff)->this.modelMapper.map(Staff, StaffRequest.class)).collect(Collectors.toList());
		return StaffRequests;
	}



	

}
