package com.cg.osm.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cg.osm.entity.User;
import com.cg.osm.error.UserNotFoundException;
import com.cg.osm.service.LoginServiceImpl;

@RestController
public class LoginController {

	/* Injecting Login Service Layer into Login Controller layer. */
	@Autowired
	private LoginServiceImpl loginService;
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	/* Method to add a userLogin */
	@PostMapping("/userlogin")
	public User addUser(@Valid @RequestBody User user) {
		logger.info("User addUser()");
		return loginService.addUser(user);
	}

	/*
	 * Method to delete a User by ID and throw appropriate exception if there is no
	 * such User
	 */
	@DeleteMapping("/deleteuser/{userId}")
	public User removeUser(@PathVariable String userId) throws UserNotFoundException {
		logger.info("User removeUser()");
		return loginService.removeUser(userId);
	}

	/*
	 * Method to Sign in by userId and password throw exception if there is no User
	 * with that userId and password
	 */
	@GetMapping("/userlogin/{userId}/{password}")
	public User userSignIn(@PathVariable String password, @PathVariable String userId) throws UserNotFoundException {
		logger.info("User userLogin()");
		return loginService.userLogin(userId, password);
	}

	/*
	 * Method to validate User by only userId throw exception if there is no User
	 * with that userId
	 */
	@GetMapping("/getuser/{userId}")
	public User validateUser(@PathVariable String userId) throws UserNotFoundException {
		logger.info("User validateUser()");
		return loginService.validateUser(userId);
	}

	/*
	 * Method to get ALL Users throw exception if there are no customers
	 */
	@GetMapping(path = "/userlogin")
	public List<User> showAllUsers() throws UserNotFoundException {
		logger.info("Customer showAllUsers()");
		return loginService.showAllUsers();
	}

	/*
	 * Updating an already existing user, if the product doesn't exist an exception
	 * is thrown
	 */
	@PutMapping(path = "/userlogin")
	public User updateUser(@Valid @RequestBody User user) throws UserNotFoundException {
		logger.info("User updateUser()");
		return loginService.updateUser(user);
	}

}