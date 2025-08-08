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
import com.blogappcode.blog.payload.UserDTO;
import com.blogappcode.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/")
public class UserController {
	@Autowired
	private UserService userService;

	// create User
	@PostMapping("/")						 //@valid annotation is used to validate the fields
	public ResponseEntity<UserDTO> createUser( @Valid @RequestBody UserDTO userDTO) {
		UserDTO createdUserDTO = this.userService.createUser(userDTO);
		return new ResponseEntity<UserDTO>(createdUserDTO, HttpStatus.CREATED);
	}

	// Put User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> upadateUser(@RequestBody UserDTO userDTO, @PathVariable("userId") Integer userId) {
		UserDTO updatedUserDTO = this.userService.updateUser(userDTO, userId);
		return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
	}

	// delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted", true),HttpStatus.OK);
	}

	// Get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer userId){
		UserDTO userDTO =this.userService.getUser(userId);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}

	// Get All Users
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return new ResponseEntity<List<UserDTO>>(this.userService.getAllUsers(), HttpStatus.OK);
	}

}
