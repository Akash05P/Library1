package com.example.Library1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Library1.entity.Staff;
import com.example.Library1.entity.Students;
import com.example.Library1.security.JwtAuthRequest;
import com.example.Library1.security.JwtAuthResponse;
import com.example.Library1.security.JwtTokenHelper;


@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	 private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	
//	 @PostMapping("/login")
//	    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) {
//
//	        this.doAuthenticate(request.getEmail(), request.getPassword());
//
//
//	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//	        String token = this.jwtTokenHelper.generateToken(userDetails);
//
//	        JwtAuthResponse response = JwtAuthResponse.builder()
//	        		.jwttoken(token)
//	                .username(userDetails.getUsername()).build();
//	        return new ResponseEntity<>(response, HttpStatus.OK);
//	    }
	 
	 

	 @PostMapping("/login")
	    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) {

	        this.doAuthenticate(request.getEmail(), request.getPassword());


	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        
	        Integer id = null;
	        String name = null;
	        List<String> roles = null;

	        if (userDetails instanceof Students) {
	        	Students student = (Students) userDetails;
	            id = student.getId();
	            name = student.getName();
	            roles = student.getStudentRoles().stream()
	                    .map(role -> role.getName())
	                    .collect(Collectors.toList());
	            
	        } else if (userDetails instanceof Staff) {
	        	Staff staff = (Staff) userDetails;
	            id = staff.getId();
	            name = staff.getName();
	         
	            roles = staff.getRoles().stream()
	                    .map(role -> role.getName())
	                    .collect(Collectors.toList());
	        }
	        String token = jwtTokenHelper.generateToken(userDetails);

	        JwtAuthResponse response = new JwtAuthResponse(
	                token, 
	                roles ,
	                userDetails.getUsername(),  // Username (which is the email)
	                  // Email
	                name, 
	                id,
	                
	                userDetails.getUsername()
	            );
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    private void doAuthenticate(String email, String password) {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	        	
	        	authenticationManager.authenticate(authentication);


	        } catch (BadCredentialsException e) {
	            throw new BadCredentialsException(" Invalid Username or Password  !!");
	        }

	    }

	    @ExceptionHandler(BadCredentialsException.class)
	    public String exceptionHandler() {
	        return "Credentials Invalid !!";
	    }
}
