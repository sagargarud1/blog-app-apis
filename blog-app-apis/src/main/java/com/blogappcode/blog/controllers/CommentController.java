package com.blogappcode.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogappcode.blog.payload.ApiResponse;
import com.blogappcode.blog.payload.CommentDTO;
import com.blogappcode.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	
	 
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,@PathVariable("postId") Integer postId){
		
		CommentDTO createdComment = this.commentService.createComment(commentDTO, postId);
		return new ResponseEntity<CommentDTO>(createdComment,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/comment/{commentId}")
	private ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted", true),HttpStatus.OK);
	}
}
