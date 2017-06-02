package com.my.system.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.my.core.model.User;
import com.my.core.service.impl.BaseServiceImpl;
import com.my.system.dao.UserDAO;
import com.my.system.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	UserDAO userDao;
	
	@Autowired  
	private PasswordEncoder passwordEncoder;  
	
	@Override
	public void save(User user){  
        user.setPassword(passwordEncoder.encode(user.getPassword()));  
        userDao.save(user);  
    }  
	

	@Override
	public User findBySso(String ssoId) {
		return userDao.findBySso(ssoId);
	}
	
	@Override
	public User findUserByNameAndPassword(String username, String password) {
		
		return userDao.findUserByNameAndPassword(username,DigestUtils.md5Hex(password));
	}

	@Override
	public void testTx(User user) {
		userDao.save(user);
	}


	@Override
	public User findById(int id) {
		return userDao.get(User.class, id);
	}

	 public static void main(String[] args) {  
         String password = "neil";  
         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  
         System.out.println(passwordEncoder.encode(password));  
         boolean match = passwordEncoder.matches("neil", "$2a$10$.Bi98gMWV9nnYw5YHE6qyOUQkWmN.khiNwvx7UWr4PGg8ezatMwT2");
         System.out.println(match);
 }

}
