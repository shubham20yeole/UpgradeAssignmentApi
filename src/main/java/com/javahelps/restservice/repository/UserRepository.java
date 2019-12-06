package com.javahelps.restservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.javahelps.restservice.entity.User;

@RestResource(exported = false)
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	public static final UserRepository UserDao = null;
}
