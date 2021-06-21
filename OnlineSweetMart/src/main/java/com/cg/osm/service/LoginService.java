package com.cg.osm.service;

import java.util.List;

import com.cg.osm.entity.User;
import com.cg.osm.error.UserNotFoundException;


public interface LoginService {
	
	public User addUser(User user);
	public User removeUser(String userId);
	public User validateUser(String userId);
	public User userLogin(String userId,String password);
	public List<User> showAllUsers() throws UserNotFoundException;
	public User updateUser(User user) throws UserNotFoundException;
}
