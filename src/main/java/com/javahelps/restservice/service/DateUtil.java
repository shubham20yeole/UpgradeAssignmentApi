package com.javahelps.restservice.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.javahelps.restservice.entity.BookingDate;

public interface DateUtil {

	// Date to String format
	public String formatDate(Date date);

	// String to Date format
	public Date formatDate(String date) throws ParseException;

	// Date to Calendar format and set defaulkt time to midnight
	public Calendar dateToCalendar(Date date);

	// Function to add days
	public Date addDays(int days, Date toDate) throws ParseException;

	// Prepocess the bookingDates as set.contains cannot find duplicates because of
	// object equality rule
	public Set<String> bookingDatesToStringSet(Set<BookingDate> bookingDates);

	// Get range between two dates
	public DateRange getDateRange(Set<BookingDate> reservations);

	// Calculate difference in days between two dates
	public int dateDifference(Date endDate, Date startDate);

	// Check if start date is valid, start date should be one day ahead of today and
	// within one month in future
	public boolean hasValidStartDate(Date startDate, Date endDate);
}
