package com.javahelps.restservice.controller;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javahelps.restservice.entity.Reservation;
import com.javahelps.restservice.entity.User;
import com.javahelps.restservice.repository.UserRepository;
import org.hibernate.Session;
import javassist.tools.web.BadHttpRequest;
@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	private UserRepository repository;

	@GetMapping
	public Iterable<User> findAll() {
		
		Reservation res1 = new Reservation();
		res1.setReservationDate(new Date());
 
		Reservation res2 = new Reservation();
		res2.setReservationDate(new Date());
 
        //Add new Employee object
        User firstUser = new User();
        firstUser.setEmail("demo-user-first@mail.com");
        firstUser.setFullname("demo-one");
 
 
        Set<Reservation> reservations = new HashSet<Reservation>();
        reservations.add(res1);
        reservations.add(res2);
        
        firstUser.setReservations(reservations);
 
        repository.save(firstUser);
		return repository.findAll();
	}

	@GetMapping(path = "/{username}")
	public User find(@PathVariable("username") String username) {
		return repository.findOne(username);
	}

	@PostMapping(consumes = "application/json")
	public User create(@RequestBody User user) {
		return repository.save(user);
	}

	@DeleteMapping(path = "/{username}")
	public void delete(@PathVariable("username") String username) {
		repository.delete(username);
	}

	@PutMapping(path = "/{fullname}")
	public User update(@PathVariable("fullname") String fullname, @RequestBody User user) throws BadHttpRequest {
		if (repository.exists(fullname)) {
			user.setFullname(fullname);
			return repository.save(user);
		} else {
			throw new BadHttpRequest();
		}
	}

}