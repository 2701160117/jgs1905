package org.jgs1905.dao;


import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.jgs1905.entity.User;
import org.jgs1905.utils.DataSourceUtil;

public class UserDao {
	// 注册
	public int insert(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "insert user(nickname, username, password) value(?, ?, ?);";
		
		int result = qr.update(sql, user.getNickname(), user.getUsername(), user.getPassword());
		return result;
	}
	
	// 登录
	public User select(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		
		String sql = "select * from user where username = ? and password = ?;";
		
		return user = qr.query(sql, new BeanHandler<>(User.class),  user.getUsername(), user.getPassword());
		
		
	}
	// 修改

	public int update(User user) throws SQLException {
		
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		
		String sql = "update user set nickname = ?, password = ?  where id = "+user.getId();
		
		
		return qr.update(sql, user.getNickname(), user.getPassword());
	}
	
}
