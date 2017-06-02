package com.my.system.dao.impl;

import java.util.List;


import org.springframework.stereotype.Repository;

import com.my.core.dao.impl.SimpleDAOHibernate;
import com.my.core.model.UserProfile;
import com.my.system.dao.UserProfileDAO;

@Repository("userProfileDao")  
public class UserProfileDaoImpl extends SimpleDAOHibernate implements UserProfileDAO{

	@Override
	public List<UserProfile> findAll() {
		String hql = "From UserProfile order by type asc";
		return (List<UserProfile>) this.find(hql);
	}

	@Override
	public UserProfile findByType(String type) {
		String hql = "From UserProfile where type = :type";
		String[] paramNames = {"type"};
		String[] values = {type};
		return (UserProfile) this.getObjectByQuery(hql, paramNames, values);
	}

	@Override
	public UserProfile findById(int id) {
		return this.get(UserProfile.class, id);
	}  
   
	
} 