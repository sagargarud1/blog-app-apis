package com.blogappcode.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogappcode.blog.entities.User;
import com.blogappcode.blog.exceptions.ResourceNotFoundException;
import com.blogappcode.blog.payload.UserDTO;
import com.blogappcode.blog.repositories.UserRepo;
import com.blogappcode.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = dtoToUser(userDTO);
		User savedUser = this.userRepo.save(user);
		return usertoUserDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow((() -> new ResourceNotFoundException("User", " id ", userId)));
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		User updatedUser = this.userRepo.save(user);
		return usertoUserDTO(updatedUser);
	}

	@Override
	public UserDTO getUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " 	Id ", userId));
		return usertoUserDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> userList = this.userRepo.findAll();
		List<UserDTO> userDTOs = userList.stream().map(user -> usertoUserDTO(user)).collect(Collectors.toList());
		return userDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		this.userRepo.delete(user);

	}

	private User dtoToUser(UserDTO userDTO) {
		User user = new User();

//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
//		user.setAbout(userDTO.getAbout());

		// ModelMapper convert userDTO to user instead of manual;
		user = modelMapper.map(userDTO, User.class);
		return user;

	}

	private UserDTO usertoUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		// ModelMapper convert user to userDTO instead of manual;
		userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

}
