package com.blogappcode.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogappcode.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
