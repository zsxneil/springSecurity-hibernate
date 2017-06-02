package com.my.system.dao;

import com.my.core.dao.BaseDAO;
import com.my.core.model.User;

public interface UserDAO extends BaseDAO {

	User findUserByNameAndPassword(String username, String md5Hex);

	User findBySso(String ssoId);

	
	
}