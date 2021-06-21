package com.cg.osm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.osm.entity.User;
import com.cg.osm.repository.LoginRepository;

@SpringBootTest
 class LoginServiceTest {

	@InjectMocks
	LoginServiceImpl loginServiceImpl;
	@Mock
	LoginRepository loginRepo;

	@Test
	 void addUserMockTest() {
		User user = new User();
		user.setUserId("Sandeep");
		user.setPassword("password");
		user.setRole("User Plus");

		Mockito.when(loginRepo.save(user)).thenReturn(user);
		assertThat(loginServiceImpl.addUser(user)).isEqualTo(user);
	}

	@Test
	 void removeUserMockTest() {
		User user = new User();
		user.setUserId("Sandeep");
		user.setPassword("password");
		user.setRole("User Plus");

		Mockito.when(loginRepo.findById(user.getUserId())).thenReturn(null);
		Mockito.when(loginRepo.existsById(user.getUserId())).thenReturn(false);
		assertFalse(loginRepo.existsById(user.getUserId()));
	}

	@Test
	 void validateUserMockTest() {
		User user = new User();
		user.setUserId("Sandeep");
		user.setPassword("password");
		user.setRole("User Plus");

		Mockito.when(loginRepo.findValidateUser(user.getUserId(), user.getPassword())).thenReturn(user);
		Mockito.when(loginRepo.existsById(user.getUserId())).thenReturn(true);
		Mockito.when(loginRepo.getPassword(user.getUserId())).thenReturn(user.getPassword());
		assertThat(loginServiceImpl.validateUser(user.getUserId())).isEqualTo(user);

	}

	@Test
	 void showAllUsersMockTest() {
		User user1 = new User();
		user1.setUserId("Sandeep");
		user1.setPassword("password");
		user1.setRole("User Plus");

		User user2 = new User();
		user2.setUserId("Asrith");
		user2.setPassword("Password");
		user2.setRole("UsersPlus");

		List<User> userlist = new ArrayList<>();
		userlist.add(user1);
		userlist.add(user2);
		Mockito.when(loginRepo.findAll()).thenReturn(userlist);
		assertThat(loginServiceImpl.showAllUsers()).isEqualTo(userlist);

	}

}
