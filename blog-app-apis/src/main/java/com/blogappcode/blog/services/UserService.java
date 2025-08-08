package com.blogappcode.blog.services;

import java.util.List;

import com.blogappcode.blog.payload.UserDTO;

public interface UserService {

	UserDTO createUser(UserDTO user);
	UserDTO updateUser(UserDTO user,Integer id);
	UserDTO getUser(Integer userId);
	List<UserDTO> getAllUsers();
	void deleteUser(Integer userId);
}
