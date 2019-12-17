package com.javahelps.restservice.controller;

import java.text.ParseException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javahelps.restservice.entity.BookingDate;
import com.javahelps.restservice.errorhandling.UserServiceException;
import com.javahelps.restservice.bookingValidations.ValidationConstants;
import com.javahelps.restservice.bookingValidations.Validations;
import com.javahelps.restservice.entity.Booking;
import com.javahelps.restservice.repository.BookingDateRepository;
import com.javahelps.restservice.repository.BookingRepository;
import com.javahelps.restservice.service.BookingDatesServiceImpl;
import com.javahelps.restservice.service.DateRange;
import com.javahelps.restservice.service.DateUtilImpl;

import javassist.tools.web.BadHttpRequest;

@CrossOrigin(origins = "http://campsiteclient.herokuapp.com/", maxAge = 3600)
@RestController
@RequestMapping(path = "/booking")
public class BookingController {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingDateRepository bookingDatesRepository;

	DateUtilImpl            dateUtils               = new DateUtilImpl();
	BookingDatesServiceImpl bookingDatesServiceImpl = new BookingDatesServiceImpl();

	@GetMapping
	public Iterable<Booking> findAll() throws ParseException {
		return bookingRepository.findAll();
	}

	@GetMapping(path = "/{userid}")
	public Booking find(@PathVariable("userid") String userId) {
		return bookingRepository.findOne(userId);
	}

	@PostMapping(consumes = "application/json")
	public Booking create(@RequestBody Booking booking)
			throws UserServiceException, ParseException {

    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
    EntityManager entityManager = emfactory.createEntityManager( );

		// Get new booking sets
		Set<BookingDate> bookingDates = booking.getBookingDates();

		/*
		 * Initiate dates locking system
		 * USE PESSIMISTIC LOCK IN JPA
		 */
		for (BookingDate bookingDate : bookingDates) {

			// Lock the date
			entityManager.lock(bookingDate, LockModeType.PESSIMISTIC_WRITE);
			
			// Define PESSIMISTIC LOCK properties
			// Lock will timeout after 2 secs
			Map<String, Object> properties = new HashMap<>(); 
			properties.put("javax.persistence.lock.timeout", 2000); 
			 
			// Set PESSIMISTIC LOCK time out
			entityManager.find(bookingDate.getClass(), bookingDate.getBookingDateId(), LockModeType.PESSIMISTIC_READ, properties);
		}

		// Get start and end dates range
		DateRange range = dateUtils.getDateRange(bookingDates);

		// Get already reserved dates if start and end date is not provided
		BookingDate reserved = bookingDatesRepository.getBooking(range.getStartDate());

		// Get conflict reservations on provided dates
		List<BookingDate> conflicts = bookingDatesRepository.getBooking(range.getStartDate(),
				range.getEndDate());

		// Send above data to Validations object to validate if reservation can be made
		Validations constantsImpl = new Validations(bookingDates, range, conflicts,
				reserved);

		// Check if valid dates are provided
		if (!constantsImpl.isValid()) {
			AbstractMap.Entry<String, Integer> validation = constantsImpl.getValidation();

			// Throw exception if invalid dates are provided
			throw new UserServiceException(validation.getKey(), validation.getValue());
		}

		try {
			// If we are here means user provided valid dates
			// Post reservation
			bookingRepository.save(booking);
		} catch (ConstraintViolationException ex) {
			throw new UserServiceException(ValidationConstants.getInvalidNameOrEmail(),
					ValidationConstants.getInvalidNameOrEmailStatus());
		}
		return booking;
	}

	@CrossOrigin(origins = "http://campsiteclient.herokuapp.com/", maxAge = 3600)
	@DeleteMapping(path = "/{bookingId}")
	public void delete(@PathVariable("bookingId") String bookingId)
			throws UserServiceException {

		Booking booking = bookingRepository.findOne(bookingId);
		if (bookingDatesServiceImpl.canWithdrawBooking(booking)) {
			bookingRepository.delete(bookingId);
			return;
		}

		throw new UserServiceException(ValidationConstants.getReservationExpired(),
				ValidationConstants.getReservationExpiredStatus());
	}

	@CrossOrigin(origins = "http://campsiteclient.herokuapp.com/", maxAge = 3600)
	@PutMapping(path = "/{bookingId}")
	public void update(@PathVariable("bookingId") String bookingId,
			@RequestBody Booking booking) throws BadHttpRequest {
		Booking existingBooking = bookingRepository.findOne(bookingId);
		if (existingBooking != null) {
			existingBooking.setBookingDates(null);
			existingBooking.setBookingDates(booking.getBookingDates());
			BookingRepository.updateBooking(booking);
		}
	}
}