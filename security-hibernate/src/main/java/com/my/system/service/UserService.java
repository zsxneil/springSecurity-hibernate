package com.my.system.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.my.core.model.User;
import com.my.core.service.BaseService;

public interface UserService extends BaseService {

	User findUserByNameAndPassword(String username, String password);

	void testTx(User user);

	User findBySso(String ssoId);
	
	@PreAuthorize("hasRole('ADMIN')")
	void save(User user);
	
	User findById(int id);

}
