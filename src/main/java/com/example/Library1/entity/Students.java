package com.example.Library1.entity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Students implements UserDetails {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String studentaddress;	
	private long studentphone;
	private String password;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student", cascade = CascadeType.ALL)
	private Set<StudentRole> studentRoles;
	
	@ManyToMany
	private Set<CatalogItem> catalogItems;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return studentRoles.stream()
				.map(role->new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	
	  @Override
	    public boolean isAccountNonExpired() {
	        return true; // Implement according to your requirements
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true; // Implement according to your requirements
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true; // Implement according to your requirements
	    }

	    @Override
	    public boolean isEnabled() {
	        return true; // Implement according to your requirements
	    }

}
