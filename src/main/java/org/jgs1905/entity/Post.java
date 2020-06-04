package org.jgs1905.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 帖子类
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
	private Long id;
	private String title;
	private Byte type;
	private String summary;
	private String content;
	private Date create_time;
	private Long user_id;
}
