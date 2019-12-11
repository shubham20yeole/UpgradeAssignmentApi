package com.javahelps.restservice.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javahelps.errorhandling.Constants;
import com.javahelps.errorhandling.UserServiceException;
import com.javahelps.restservice.entity.BookingDate;
import com.javahelps.restservice.entity.Booking;
import com.javahelps.restservice.repository.BookingDateRepository;
import com.javahelps.restservice.repository.BookingRepository;
import com.javahelps.service.DateUtilImpl;
import com.javahelps.service.BookingDatesServiceImpl;
import com.javahelps.service.DateRange;

import org.hibernate.Session;
import javassist.tools.web.BadHttpRequest;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
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

		Set<BookingDate>  bookingDates = booking.getBookingDates();
		List<BookingDate> shs          = new ArrayList<BookingDate>(bookingDates);

		if (bookingDates.size() == 0)
			throw new UserServiceException(Constants.DATES_NOT_FOUND,
					Constants.DATES_NOT_FOUND_STATUS);
		
		if (bookingDates.size() > 3)
			throw new UserServiceException(Constants.DATES_LONG_DURATION,
					Constants.DATES_LONG_DURATION_STATUS);

		DateRange range = dateUtils.getDateRange(bookingDates);

		if (range.isSingleDayReservation()) {
			BookingDate reserved = bookingDatesRepository.getBooking(range.getStartDate());
			if (reserved != null)
				throw new UserServiceException(Constants.DATES_UNAVAILABLE,
						Constants.DATES_UNAVAILABLE_STATUS);
		}

		if (!range.hasValidStartDate()) {
			throw new UserServiceException(Constants.DATES_INVALID,
					Constants.DATES_INVALID_STATUS);
		}

		List<BookingDate> conflicts = bookingDatesRepository.getBooking(range.getStartDate(),
				range.getEndDate());
		if (conflicts.size() > 0)
			throw new UserServiceException(Constants.DATES_UNAVAILABLE,
					Constants.DATES_UNAVAILABLE_STATUS);

		if (!range.isThreeDaysReservation()) {
			throw new UserServiceException(Constants.DATES_MAX_DURATION_EXCEED,
					Constants.DATES_MAX_DURATION_EXCEED_STATUS);
		}
		
		try {
			bookingRepository.save(booking);
		} catch(ConstraintViolationException ex) {
			throw new UserServiceException(Constants.INVALID_NAME_OR_EMAIL,
					Constants.INVALID_NAME_OR_EMAIL_STATUS);
		}
		return booking;
	}

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
	@DeleteMapping(path = "/{bookingId}")
	public void delete(@PathVariable("bookingId") String bookingId)
			throws UserServiceException {

		Booking booking = bookingRepository.findOne(bookingId);
		if (bookingDatesServiceImpl.canWithdrawBooking(booking)) {
			bookingRepository.delete(bookingId);
			return;
		}

		throw new UserServiceException(Constants.RESERVATION_EXPIRED,
				Constants.RESERVATION_EXPIRED_STATUS);
	}

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
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