package org.jgs1905.service;

import java.sql.SQLException;

import org.jgs1905.dao.UserDao;
import org.jgs1905.entity.User;

public class UserService {
	private UserDao  userDao = new UserDao();
	
	//注册
	public int regist(User user) throws SQLException {
		
		return userDao.insert(user);
	}
	//登录
	public User login(User user) throws SQLException {
		
		return userDao.select(user);
	}
	//修改
	public int update(User user) throws SQLException {
		
		return userDao.update(user);
	}
	
}
