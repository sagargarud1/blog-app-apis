package com.blogappcode.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogappcode.blog.payload.CategoryDTO;
@Service
public interface CategoryService {

	//create
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	//update
	CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer categoryId);
	
	//get category
	CategoryDTO getCategory(Integer categoryId);
	
	//get all categories
	List<CategoryDTO> getAllCategories();
	
	//delete
	void deleteCategory(Integer categoryId);
	
	
}
