package org.jgs1905.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.jgs1905.entity.Comment;
import org.jgs1905.utils.DataSourceUtil;

/**
 * 评论操作类
 * @author Administrator
 *
 */
public class CommentDao {
	/**
	 * 	根据帖子id查询所有评论
	 * @param postId
	 * @return
	 * @throws SQLException 
	 */
	public List<Comment> findByPostId(Long postId) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		
		String sql = "SELECT `comment`.*,`user`.nickname FROM comment LEFT JOIN `user` ON `comment`.user_id = `user`.id WHERE `comment`.post_id = 1";
		
		List<Comment> result = qr.query(sql, new BeanListHandler<>(Comment.class), postId);
		
		return result;
	}
	
	/**
	 * 
	 * @param comment
	 * @return
	 * @throws SQLException 
	 */
	public int insert(Comment comment) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "insert into comment(content,create_time,user_id,post_id) value(?,?,?,?)";
		int result = qr.update(sql, comment.getContent(), comment.getCreate_time(), comment.getUser_id(), comment.getPost_id());
		return result;
	}

	public int delete(Long id) throws SQLException {

		
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "delete form comment where id = "+id;
		int result = qr.update(sql);
		return result;
	}
}
