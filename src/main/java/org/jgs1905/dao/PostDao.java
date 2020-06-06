package org.jgs1905.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.jgs1905.entity.Post;
import org.jgs1905.utils.DataSourceUtil;

/**
 * 帖子数据操作类
 * @author Administrator
 *
 */
public class PostDao {
	/**
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public List<Post> findList() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "SELECT post.*,`user`.nickname,COUNT(post_id) AS comment_count FROM post INNER JOIN `user` ON post.user_id = `user`.id LEFT JOIN `comment` ON post.id = `comment`.post_id GROUP BY post.id ORDER BY post.create_time DESC";
		List<Post> result = qr.query(sql, new BeanListHandler<>(Post.class));
		return result;
	}

	/**
	 * 
	 * @param post
	 * @return
	 * @throws SQLException 
	 */
	public int insert(Post post) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "insert into post(title,type,summary,content,create_time,user_id) value(?,?,?,?,?,?)";
		int result = qr.update(sql, post.getTitle(), post.getType(), post.getSummary(), post.getContent(), post.getCreate_time(), post.getUser_id());
		return result;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public Post findOneById(Long id) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql = "select * from post where id = ?";
		Post result = qr.query(sql, new BeanHandler<>(Post.class), id);
		return result;
	}
}
