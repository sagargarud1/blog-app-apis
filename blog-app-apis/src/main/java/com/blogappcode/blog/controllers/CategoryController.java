package com.blogappcode.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogappcode.blog.payload.ApiResponse;
import com.blogappcode.blog.payload.CategoryDTO;
import com.blogappcode.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO cat = this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(cat,HttpStatus.CREATED);
	}
	
	
	// update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable("categoryId") Integer categoryId){
		CategoryDTO cat = this.categoryService.updateCategory(categoryDTO, categoryId);
		return new ResponseEntity<CategoryDTO>(cat,HttpStatus.OK);
	}
	
	
	
	// delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted", true),HttpStatus.OK);
	}
	
	
	// get one
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getOneCategory(@PathVariable("categoryId") Integer categoryId){
		CategoryDTO cat = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDTO>(cat, HttpStatus.OK);				
	}

	
	
	// get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategory(){
		List<CategoryDTO> categoryList = this.categoryService.getAllCategories();
		return new ResponseEntity<List<CategoryDTO>>(categoryList,HttpStatus.OK);
	}

}
