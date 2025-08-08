package com.blogappcode.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogappcode.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
