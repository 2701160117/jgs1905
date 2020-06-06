package org.jgs1905.service;

import java.sql.SQLException;

import org.jgs1905.dao.CommentDao;
import org.jgs1905.entity.Comment;

/**
 * 
 * 评论业务类
 * @author Administrator
 *
 */
public class CommentService {
	
	private CommentDao commentDao = new CommentDao();
	/**
	 * @param comment
	 * @return 添加一个评论
	 * @throws SQLException 
	 */
	public int add(Comment comment) throws SQLException {
		return commentDao.insert(comment);
	}
	/**
	 * 删除一个帖子
	 * @param id
	 * @return 1删除成功，0删除失败
	 * @throws SQLException
	 */
	public int delete(Long id) throws SQLException {
		return commentDao.delete(id);
	}
}
