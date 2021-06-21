package com.cg.osm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.osm.entity.User;

@Repository
public interface LoginRepository extends JpaRepository<User, String> {

	@Query("select u from User u where u.userId=:userId and u.password=:password")
	public User findValidateUser(@Param("userId") String userId, @Param("password") String password);

	@Query("select u.password from User u where u.userId=:userId")
	public String getPassword(@Param("userId") String userId);
}
