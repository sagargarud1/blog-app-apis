package com.blogappcode.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogappcode.blog.entities.Category;
import com.blogappcode.blog.entities.Post;
import com.blogappcode.blog.entities.User;
import com.blogappcode.blog.exceptions.ResourceNotFoundException;
import com.blogappcode.blog.payload.PostDTO;
import com.blogappcode.blog.payload.PostResponsePage;
import com.blogappcode.blog.repositories.CategoryRepo;
import com.blogappcode.blog.repositories.PostRepo;
import com.blogappcode.blog.repositories.UserRepo;
import com.blogappcode.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer categoryId, Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Post post =this.modelMapper.map(postDTO, Post.class);
		post.setImageName("default.jpg");
		post.setAddedDate(new Date());
		post.setImageName("default.jpg");
		post.setAddedDate(new Date());	
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDTO.class);
	}
	
	
	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		
		Post updatedPost=this.postRepo.save(post);		
		
		return this.modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post  = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PostResponsePage getAllPosts(Integer pageSize,Integer pageNumber,String sortBy,String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> allposts =  this.postRepo.findAll(pageable);
		List<Post> posts = allposts.getContent();	
		List<PostDTO> postDTOs=posts.stream().map((post)->modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		PostResponsePage postResponsePage = new PostResponsePage();
		postResponsePage.setContent(postDTOs);
		postResponsePage.setPageNumber(allposts.getNumber());
		postResponsePage.setPageSize(allposts.getSize());
		postResponsePage.setTotalElements(allposts.getTotalElements());
		postResponsePage.setTotalPages(allposts.getTotalPages());
		postResponsePage.setLastPage(allposts.isLast());
		
		return postResponsePage;
	}

	@Override
	public List<PostDTO> getPostByCategory(Integer categoryId) {
		Category cat  = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDTO> postDTOs=posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return postDTOs;
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		User user  = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDTO> postDTOs=posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		
		return postDTOs;
	}

	@Override
	public List<PostDTO> searchByTitle(String title){
		
		List<Post> posts = this.postRepo.findByTitleContaining(title);
		List<PostDTO> postDTOs = posts.stream().map((post)->modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return postDTOs;
	}

}
