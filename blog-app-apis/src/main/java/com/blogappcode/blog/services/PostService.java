package com.blogappcode.blog.services;

import java.util.List;

import com.blogappcode.blog.payload.PostDTO;
import com.blogappcode.blog.payload.PostResponsePage;

public interface PostService {

	// create
	PostDTO createPost(PostDTO postDTO, Integer categoryId, Integer userId);

	// update
	PostDTO updatePost(PostDTO postDTO, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get post
	PostDTO getPostById(Integer postId);

	// List of Post
	PostResponsePage getAllPosts(Integer pageSize , Integer pageNumber,String sortBy,String sortDir);

	// get All post by category
	List<PostDTO> getPostByCategory(Integer categoryId);

	// get All Post by User
	List<PostDTO> getPostByUser(Integer userId);
	
	//search posts
	
	List<PostDTO> searchByTitle(String title);
}
