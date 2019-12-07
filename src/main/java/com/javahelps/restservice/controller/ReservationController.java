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
import com.javahelps.restservice.repository.ReservationRepository;
import com.javahelps.restservice.repository.UserRepository;
import org.hibernate.Session;
import javassist.tools.web.BadHttpRequest;
@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

	@Autowired
	private ReservationRepository repository;

	@GetMapping
	public Iterable<Reservation> findAll() {
		return repository.findAll();
	}

//	@GetMapping(path = "/{username}")
//	public User find(@PathVariable("username") String username) {
//		return repository.findOne(username);
//	}
//
//	@PostMapping(consumes = "application/json")
//	public User create(@RequestBody User user) {
//		return repository.save(user);
//	}
//
//	@DeleteMapping(path = "/{username}")
//	public void delete(@PathVariable("username") String username) {
//		repository.delete(username);
//	}
//
//	@PutMapping(path = "/{fullname}")
//	public User update(@PathVariable("fullname") String fullname, @RequestBody User user) throws BadHttpRequest {
//		if (repository.exists(fullname)) {
//			user.setFullname(fullname);
//			return repository.save(user);
//		} else {
//			throw new BadHttpRequest();
//		}
//	}

}