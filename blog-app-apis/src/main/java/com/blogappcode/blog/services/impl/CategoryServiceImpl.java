package com.blogappcode.blog.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogappcode.blog.entities.Category;
import com.blogappcode.blog.exceptions.ResourceNotFoundException;
import com.blogappcode.blog.payload.CategoryDTO;
import com.blogappcode.blog.repositories.CategoryRepo;
import com.blogappcode.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {

		Category cat = this.modelMapper.map(categoryDTO, Category.class);
		Category addedCategory = this.categoryRepo.save(cat);

		return this.modelMapper.map(addedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		cat.setCategoryTitle(categoryDTO.getCategoryTitle());
		cat.setCategoryDescription(categoryDTO.getCategoryDescription());

		Category updatedCategory = this.categoryRepo.save(cat);

		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
		return this.modelMapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<CategoryDTO> allCatListDTO = this.categoryRepo.findAll().stream().map(cat->this.modelMapper.map(cat, CategoryDTO.class)).toList();
		return allCatListDTO;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category", categoryId));
		this.categoryRepo.delete(cat);
	}

}
