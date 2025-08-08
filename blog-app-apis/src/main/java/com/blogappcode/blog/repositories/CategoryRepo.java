package com.blogappcode.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogappcode.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
