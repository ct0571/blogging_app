package com.coding.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.blog.entities.User;
import com.coding.blog.exceptions.ResourceNotFoundException;
import com.coding.blog.payloads.UserDto;
import com.coding.blog.repositories.UserRepo;
import com.coding.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	// Injecting the UserRepo which is a Spring Data JPA interface to interact with
	// DB(like save, findById ...)
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	// Takes UserDto object from controller (typically from REST request body)
	@Override
	public UserDto createUser(UserDto user) {

		User username = this.dtoToUser(user); // convert DTO to entity because DB can only save entities
		User savedUser = this.userRepo.save(username); // save to DB

		return this.userToDto(savedUser); // convert entity back to DTO to return only necessary fields to the client
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);

		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();

		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);

	}

	// converting userDto to user Entity and setting the values in newly created
	// user object by getting userDto values
	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

		/**
		 * user.setId(userDto.getId()); user.setName(userDto.getName());
		 * user.setEmail(userDto.getEmail()); user.setPassword(userDto.getPassword());
		 * user.setAbout(userDto.getAbout());
		 **/
		return user;

	}

	// converting user Entity back to userDto after saving in the DB using
	// userRepo.save function and
	// storing these converted userDto values in newly created userDto object
	// converting from DB to API response
	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);

		/**
		 * userDto.setId(user.getId()); userDto.setName(user.getName());
		 * userDto.setEmail(user.getEmail()); userDto.setPassword(user.getPassword());
		 * userDto.setAbout(user.getAbout());
		 **/
		return userDto;
	}

}
