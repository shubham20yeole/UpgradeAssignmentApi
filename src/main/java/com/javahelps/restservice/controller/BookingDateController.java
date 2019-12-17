package com.javahelps.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.javahelps.restservice.entity.Booking;
import com.javahelps.restservice.entity.BookingDate;
import com.javahelps.restservice.repository.BookingDateRepository;
import com.javahelps.restservice.service.BookingDatesServiceImpl;
import com.javahelps.restservice.service.DateRange;
import com.javahelps.restservice.service.DateUtilImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@CrossOrigin(origins = "http://campsiteclient.herokuapp.com", maxAge = 3600)
@RestController
@RequestMapping(path = "/booking/dates")
public class BookingDateController {

	@Autowired
	private BookingDateRepository   bookingDateRepository;
	private DateUtilImpl            dateUtils          = new DateUtilImpl();
	private BookingDatesServiceImpl bookingServiceImpl = new BookingDatesServiceImpl();

	/*
	 * The users will need to find out when the campsite is available. So the system
	 * should expose an API to provide information of the availability of the
	 * campsite. for a given date range with the default being 1 month.
	 */
	@CrossOrigin(origins = "http://campsiteclient.herokuapp.com", maxAge = 3600)
	@GetMapping
	public JSONArray checkAvaibility(@RequestParam(value = "from") Optional<String> from,
			@RequestParam(value = "to") Optional<String> to) throws java.text.ParseException {

		String startDateStringFormat = from
				.orElse(dateUtils.formatDate(dateUtils.addDays(1, new Date())));
		String endDateStringFormat   = to
				.orElse(dateUtils.formatDate(dateUtils.addDays(31, new Date())));

		Date startDate = bookingServiceImpl.formatDate(startDateStringFormat);
		Date endDate   = dateUtils.formatDate(endDateStringFormat);

		List<BookingDate> bookedDates = bookingDateRepository.getBooking(startDate, endDate);

		List<String> list = new ArrayList<String>();

		for (BookingDate bookingDate : bookedDates) {
			Date   date  = bookingDate.getBookingDate();
			String dateToString = dateUtils.formatDate(date);
			list.add(dateToString);
		}

		JSONArray result = bookingServiceImpl
				.getAvailableDates(new HashSet<BookingDate>(bookedDates), startDate, endDate);
		return result;
	}

	// PRIVATE METHOD TO GET ALL RESERVED DATES FOR THIS CLASS ACCESS ONLY
	private JSONArray findAll() throws ParseException, java.text.ParseException {
		return (JSONArray) new JSONParser()
				.parse(new Gson().toJson(bookingDateRepository.findCampsiteVacancy()));
	}
}