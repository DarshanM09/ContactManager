package com.SpringBoot.contact.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SpringBoot.contact.Model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.email= :email")
	public User getUserName(@Param("email") String email);

}
