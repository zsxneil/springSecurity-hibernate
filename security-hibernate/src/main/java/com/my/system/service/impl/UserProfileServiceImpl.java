package com.my.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.core.model.UserProfile;
import com.my.core.service.impl.BaseServiceImpl;
import com.my.system.dao.UserProfileDAO;
import com.my.system.service.UserProfileService;

@Service("userProfileService")  
public class UserProfileServiceImpl extends BaseServiceImpl implements UserProfileService{  
       
    @Autowired  
    UserProfileDAO userProfileDAO;  
       
    public List<UserProfile> findAll() {  
        return userProfileDAO.findAll();  
    }  
   
    public UserProfile findByType(String type){  
        return userProfileDAO.findByType(type);  
    }  
   
    public UserProfile findById(int id) {  
        return userProfileDAO.findById(id);  
    }  
} 
