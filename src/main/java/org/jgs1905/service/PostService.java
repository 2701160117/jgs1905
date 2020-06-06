package org.jgs1905.service;

import java.sql.SQLException;
import java.util.List;

import org.jgs1905.dao.CommentDao;
import org.jgs1905.dao.PostDao;
import org.jgs1905.entity.Comment;
import org.jgs1905.entity.Post;

/**
 * 帖子业务类
 * @author Administrator
 *
 */
public class PostService {
	private PostDao postDao = new PostDao();
	
	private CommentDao commentDao = new CommentDao();

	/**
	 * 
	 * @return 返回所有帖子
	 * @throws SQLException 
	 */
	public List<Post> getList() throws SQLException {
		return postDao.findList();
	}

	/**
	 * 
	 * @param post
	 * @return 添加一个帖子
	 * @throws SQLException 
	 */
	public int add(Post post) throws SQLException {
		return postDao.insert(post);
	}

	/**
	 * 
	 * @param id
	 * @return 通过id查找一个帖子
	 * @throws SQLException 
	 */
	public Post getById(Long id) throws SQLException {
		Post post = postDao.findOneById(id);
		List<Comment> comments = commentDao.findByPostId(post.getId());
		post.setComments(comments);
		return post;
	}
}
