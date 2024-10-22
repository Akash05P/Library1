package com.example.Library1.service;

import java.util.List;

import com.example.Library1.bean.StaffRequest;

public interface StaffService {
	
	StaffRequest createstaff(StaffRequest request);
	StaffRequest upadatestaff(StaffRequest request,Integer staffId);
	StaffRequest getstaffById(Integer staffId);
	List<StaffRequest> getAllstaffs(Integer pageNumber,Integer pageSize);
	void deletestaff(Integer staffId);
	List<StaffRequest> searchStaff(String keyword);

}
