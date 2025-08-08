package com.blogappcode.blog.services;

import com.blogappcode.blog.payload.CommentDTO;

public interface CommentService {

	CommentDTO createComment(CommentDTO commentDTO ,Integer postId);
	
	void deleteComment(Integer commentId);
}
