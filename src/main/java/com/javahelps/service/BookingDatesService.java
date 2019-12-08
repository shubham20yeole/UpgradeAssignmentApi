package com.javahelps.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import org.json.simple.JSONArray;

import com.javahelps.restservice.entity.BookingDate;

public interface BookingDatesService {
	public JSONArray getAvailableDates(Set<BookingDate> bookedDates, Date startDate,
			Date endDate) throws ParseException;
}
