package com.my.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.my.core.dao.impl.SimpleDAOHibernate;
import com.my.core.model.User;
import com.my.system.dao.UserDAO;


@Repository("userDAO")
public class UserDAOHibernate extends SimpleDAOHibernate implements UserDAO {

	@Override
	public User findUserByNameAndPassword(String username, String password) {
		String hql = "From User where username=:username and password=:password";
		String[] paramNames = {"username","password"};
		String[] values = {username,password};
		return (User) this.getObjectByQuery(hql, paramNames, values);
	}

	@Override
	public User findBySso(String ssoId) {
		String hql = "From User where ssoId = :ssoId";
		String[] paramNames = {"ssoId"};
		String[] values = {ssoId};
		return (User) this.getObjectByQuery(hql, paramNames, values);
	}

}
