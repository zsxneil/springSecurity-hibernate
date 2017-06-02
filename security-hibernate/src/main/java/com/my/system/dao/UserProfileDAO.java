package com.my.system.dao;

import java.util.List;

import com.my.core.dao.BaseDAO;
import com.my.core.model.UserProfile;

public interface UserProfileDAO extends BaseDAO{  
	   
    List<UserProfile> findAll();  
       
    UserProfile findByType(String type);  
       
    UserProfile findById(int id);  
} 
