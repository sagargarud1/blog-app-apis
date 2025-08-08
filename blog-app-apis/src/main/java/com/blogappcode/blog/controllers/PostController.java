package com.blogappcode.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogappcode.blog.config.AppConstants;
import com.blogappcode.blog.payload.ApiResponse;
import com.blogappcode.blog.payload.PostDTO;
import com.blogappcode.blog.payload.PostResponsePage;
import com.blogappcode.blog.services.FileService;
import com.blogappcode.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Sagar_Garud
 *
 */
@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private  String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost( @RequestBody PostDTO postDTO,@PathVariable("categoryId") Integer categoryId,@PathVariable("userId") Integer userId){
		
		PostDTO createdPost = this.postService.createPost(postDTO, categoryId, userId);
		return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
	}
	
	//GET posts by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable("userId") Integer userId){
		
		List<PostDTO> postDTOs = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDTO>>(postDTOs, HttpStatus.OK);
	}

	//GET posts by Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getALlPostsByCategory(@PathVariable("categoryId") Integer categoryId){
		List<PostDTO> postDTOs = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(postDTOs, HttpStatus.OK);
	}
	
	//GET all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponsePage> getAllPosts(
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy, 
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir) {
		PostResponsePage postResponsePage = this.postService.getAllPosts(pageSize, pageNumber,sortBy,sortDir);
		return new ResponseEntity<PostResponsePage>(postResponsePage, HttpStatus.OK);
	}
	
	//GET post by Id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostDTOById(@PathVariable("postId") Integer postId) {
		PostDTO postDTO = this.postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);		
	}
	
	//delete Post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted", true), HttpStatus.OK);
	}
	
	//update Post
	@PutMapping("posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,@PathVariable("postId") Integer postId){
		PostDTO updatedpostDTO = this.postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(updatedpostDTO,HttpStatus.OK);
	}
	
	//search by title
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchByTitle(@PathVariable("keywords") String keywords){
		List<PostDTO> searchByTitle = this.postService.searchByTitle(keywords);
		return new ResponseEntity<List<PostDTO>>(searchByTitle, HttpStatus.OK);
	}

	
	// upload image
	@PostMapping("post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadImage(@RequestParam("image") MultipartFile file,
			@PathVariable("postId") Integer postId) throws IOException {
		
		PostDTO postDTO = this.postService.getPostById(postId);

		String fileName = this.fileService.uploadFile(path, file);
		postDTO.setImageName(fileName);

		PostDTO updatePost = this.postService.updatePost(postDTO, postId);

		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		InputStream resourse = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resourse, response.getOutputStream());
	}
}

