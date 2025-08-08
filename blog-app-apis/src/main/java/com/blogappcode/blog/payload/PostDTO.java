package com.blogappcode.blog.payload;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	
	private CategoryDTO category;
	
	private UserDTO user;
//	
//	private Set<Comment> comments = new HashSet<>();
}

