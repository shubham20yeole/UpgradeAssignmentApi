package com.javahelps.restservice.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javahelps.errorhandling.ErrorCode;
import com.javahelps.errorhandling.UserServiceException;
import com.javahelps.restservice.entity.Reservation;
import com.javahelps.restservice.entity.User;
import com.javahelps.restservice.repository.ReservationRepository;
import com.javahelps.restservice.repository.UserRepository;
import com.javahelps.service.DateImplementation;
import com.javahelps.service.DateRange;

import org.hibernate.Session;
import javassist.tools.web.BadHttpRequest;
@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	DateImplementation dateUtils = new DateImplementation();

	@GetMapping
	public Iterable<User> findAll() throws ParseException {
		return userRepository.findAll();
	}

	@GetMapping(path = "/{userid}")
	public User find(@PathVariable("userid") String userId) {
		return userRepository.findOne(userId);
	}

	@PostMapping(consumes = "application/json")
	public User create(@RequestBody User user) throws UserServiceException, ParseException {

		Set<Reservation> reservations = user.getReservations();
		if (reservations.size() == 0) throw new UserServiceException(ErrorCode.DATES_NOT_FOUND, ErrorCode.DATES_NOT_FOUND_STATUS);

		DateRange range = dateUtils.getResDateRange(reservations);

		if (range.isSingleDayReservation()) {
			List<Reservation> reserved = reservationRepository.isVcancyAvailable(range.getStartDate(), dateUtils.addDays(1, range.getStartDate()).getTime());
			if(reserved != null) throw new UserServiceException(ErrorCode.DATES_UNAVAILABLE, ErrorCode.DATES_UNAVAILABLE_STATUS);
		} 

		if(!range.hasValidStartDate()) {
			throw new UserServiceException(ErrorCode.DATES_INVALID, ErrorCode.DATES_INVALID_STATUS);
		}

		List<Reservation> conflicts = reservationRepository.isVcancyAvailable(range.getStartDate(), range.getEndDate());
		if (conflicts.size() > 0) throw new UserServiceException(ErrorCode.DATES_UNAVAILABLE, ErrorCode.DATES_UNAVAILABLE_STATUS);

		if(!range.isThreeDaysReservation()) {
			throw new UserServiceException(ErrorCode.DATES_MAX_DURATION_EXCEED, ErrorCode.DATES_MAX_DURATION_EXCEED_STATUS);
		}

		return userRepository.save(user);
	}

	@DeleteMapping(path = "/{userId}")
	public void delete(@PathVariable("userId") String userId) {
		userRepository.delete(userId);
	}

	@PutMapping(path = "/{fullname}")
	public User update(@PathVariable("fullname") String fullname, @RequestBody User user) throws BadHttpRequest {
		if (userRepository.exists(fullname)) {
			user.setFullname(fullname);
			return userRepository.save(user);
		} else {
			throw new BadHttpRequest();
		}
	}

}