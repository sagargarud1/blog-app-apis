package com.blogappcode.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogappcode.blog.entities.Comment;
import com.blogappcode.blog.entities.Post;
import com.blogappcode.blog.exceptions.ResourceNotFoundException;
import com.blogappcode.blog.payload.CommentDTO;
import com.blogappcode.blog.repositories.CommentRepo;
import com.blogappcode.blog.repositories.PostRepo;
import com.blogappcode.blog.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {

		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "post Id", postId));

		Comment comment = this.modelMapper.map(commentDTO, Comment.class);

		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment  = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "comment Id", commentId));
		this.commentRepo.delete(comment);
		
	} 
}
