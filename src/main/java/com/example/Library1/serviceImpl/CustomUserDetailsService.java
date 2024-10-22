package com.example.Library1.serviceImpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Library1.entity.Staff;
import com.example.Library1.entity.Students;
import com.example.Library1.repository.StaffRepo;
import com.example.Library1.repository.StudentsRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private StudentsRepo repo;
	
	@Autowired
	private StaffRepo staffrepo;
	
	public CustomUserDetailsService (StudentsRepo studentRepo,StaffRepo staffRepo) {
		this.repo=studentRepo;
		this.staffrepo=staffRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Students student= repo.findByEmail(email);
		if(student!=null) {

			return student;
		}
		   Staff staff = staffrepo.findByEmail(email);
	        if (staff != null) {
	            return staff;
	        }
	        throw new UsernameNotFoundException("User not found with email: " + email);
		  
	}


	
}
