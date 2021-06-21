package com.cg.osm.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.osm.entity.User;
import com.cg.osm.error.UserNotFoundException;

import com.cg.osm.repository.LoginRepository;

@Service
public class LoginServiceImpl implements LoginService {

	/*
	 * Injecting Login Repository into Service Layer
	 */

	@Autowired
	LoginRepository loginRepo;
	Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Override
	@Transactional
	public User addUser(User user) {
		logger.info("User addUser()");
		loginRepo.save(user);
		return loginRepo.save(user);
	}

	@Override
	@Transactional
	public User removeUser(String userId) throws UserNotFoundException {
		logger.info("User removeUser()");
		Optional<User> users = loginRepo.findById(userId);
		if (!users.isPresent())
			throw new UserNotFoundException("NotFound");
		else {
			loginRepo.deleteById(userId);
			return users.get();
		}
	}

	@Override
	@Transactional
	public User validateUser(String userId) throws UserNotFoundException {
		logger.info("User validateUser()");
		String pass = loginRepo.getPassword(userId);
		User u = loginRepo.findValidateUser(userId, pass);
		if (u == null)
			throw new UserNotFoundException("NotFound");
		else
			return u;
	}

	@Override
	@Transactional
	public List<User> showAllUsers() throws UserNotFoundException {
		logger.info("Customer showAllCustomers()");
		List<User> userList = loginRepo.findAll();
		if (userList.isEmpty()) {
			throw new UserNotFoundException("No such users found");
		} else {
			return userList;
		}
	}

	@Override
	@Transactional
	public User userLogin(String userId, String password) throws UserNotFoundException {

		logger.info("User userLogin()");

		User user = loginRepo.findValidateUser(userId, password);
		if (user == null) {
			throw new UserNotFoundException("NotFound");
		} else
			return user;

	}

	@Override
	@Transactional
	public User updateUser(User user) throws UserNotFoundException {
		logger.info("User updateUser()");
		String userId = user.getUserId();
		boolean found = loginRepo.existsById(userId);
		if (found) {
			user.setPassword(user.getPassword());
			return loginRepo.save(user);
		} else
			throw new UserNotFoundException("No such category found");

	}
}
