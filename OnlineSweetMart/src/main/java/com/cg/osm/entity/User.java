package com.cg.osm.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Users")
public class User {

	@Id
	private String userId;
	@NotEmpty(message = "password should not empty")
	private String password;
	@NotEmpty(message = "role should not empty")
	private String role;

	public User() {

	}

	

	public User(String userId, @NotEmpty(message = "password should not empty") String password,
			@NotEmpty(message = "role should not empty") String role) {
		super();
		this.userId = userId;
		this.password = password;
		this.role = role;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Login [loginId=" + userId + ", password=" + password + ", role=" + role + "]";
	}

}