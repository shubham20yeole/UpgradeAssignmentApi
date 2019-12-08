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

import com.javahelps.errorhandling.Errors;
import com.javahelps.errorhandling.UserServiceException;
import com.javahelps.restservice.entity.BookingDate;
import com.javahelps.restservice.entity.Booking;
import com.javahelps.restservice.repository.BookingDateRepository;
import com.javahelps.restservice.repository.BookingRepository;
import com.javahelps.service.DateImplementation;
import com.javahelps.service.DateRange;

import org.hibernate.Session;
import javassist.tools.web.BadHttpRequest;
@RestController
@RequestMapping(path = "/users")
public class BookingController {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingDateRepository bookingDatesRepository;

	DateImplementation dateUtils = new DateImplementation();

	@GetMapping
	public Iterable<Booking> findAll() throws ParseException {
		return bookingRepository.findAll();
	}

	@GetMapping(path = "/{userid}")
	public Booking find(@PathVariable("userid") String userId) {
		return bookingRepository.findOne(userId);
	}

	@PostMapping(consumes = "application/json")
	public Booking create(@RequestBody Booking booking) throws UserServiceException, ParseException {

		Set<BookingDate> bookingDates = booking.getBookingDates();
		if (bookingDates.size() == 0) throw new UserServiceException(Errors.DATES_NOT_FOUND, Errors.DATES_NOT_FOUND_STATUS);

		DateRange range = dateUtils.getResDateRange(bookingDates);

		if (range.isSingleDayReservation()) {
			List<BookingDate> reserved = bookingDatesRepository.isVcancyAvailable(range.getStartDate(), dateUtils.addDays(1, range.getStartDate()).getTime());
			if(reserved != null) throw new UserServiceException(Errors.DATES_UNAVAILABLE, Errors.DATES_UNAVAILABLE_STATUS);
		} 

		if(!range.hasValidStartDate()) {
			throw new UserServiceException(Errors.DATES_INVALID, Errors.DATES_INVALID_STATUS);
		}

		List<BookingDate> conflicts = bookingDatesRepository.isVcancyAvailable(range.getStartDate(), range.getEndDate());
		if (conflicts.size() > 0) throw new UserServiceException(Errors.DATES_UNAVAILABLE, Errors.DATES_UNAVAILABLE_STATUS);

		if(!range.isThreeDaysReservation()) {
			throw new UserServiceException(Errors.DATES_MAX_DURATION_EXCEED, Errors.DATES_MAX_DURATION_EXCEED_STATUS);
		}

		return bookingRepository.save(booking);
	}

	@DeleteMapping(path = "/{userId}")
	public void delete(@PathVariable("userId") String userId) {
		bookingRepository.delete(userId);
	}

	@PutMapping(path = "/{fullname}")
	public Booking update(@PathVariable("fullname") String fullname, @RequestBody Booking user) throws BadHttpRequest {
		if (bookingRepository.exists(fullname)) {
			user.setFullname(fullname);
			return bookingRepository.save(user);
		} else {
			throw new BadHttpRequest();
		}
	}

}