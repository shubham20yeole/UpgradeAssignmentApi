package com.javahelps.restservice.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import org.json.simple.JSONArray;

import com.javahelps.restservice.entity.Booking;
import com.javahelps.restservice.entity.BookingDate;
import com.javahelps.restservice.errorhandling.UserServiceException;

public interface BookingDatesService {
	
	// Get left outer join of reserved and all dates
	public JSONArray getAvailableDates(Set<BookingDate> bookedDates, Date startDate,
			Date endDate) throws ParseException;

	// Check if dates are in past
	public boolean hasExpiredBookingDates(Booking booking) throws UserServiceException;

	// Past bookings could not be withdrawn/canceled, check it in this method
	public boolean canWithdrawBooking(Booking booking) throws UserServiceException;
}
