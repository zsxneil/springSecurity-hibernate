package com.my.system.service;

import java.util.List;

import com.my.core.model.UserProfile;
import com.my.core.service.BaseService;

public interface UserProfileService extends BaseService {  
	   
    List<UserProfile> findAll();  
       
    UserProfile findByType(String type);  
       
    UserProfile findById(int id);  
}  
