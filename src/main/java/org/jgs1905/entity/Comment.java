package org.jgs1905.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 评论实体类
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
	private Long id;
	
	private String content;
	
	private Date create_time;
	
	private Long user_id;
	
	private Long post_id;
	
	private String nickname;
	
}
