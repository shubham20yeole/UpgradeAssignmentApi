package com.javahelps.restservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.json.simple.JSONArray;

import com.javahelps.restservice.bookingValidations.ValidationConstants;
import com.javahelps.restservice.entity.BookingDate;

public class DateUtilImpl implements DateUtil {

	// Date to String format
	public String formatDate(Date date) {
		return new SimpleDateFormat(ValidationConstants.getAllowedDateFormat()).format(date);
	}

	// String to Date format
	public Date formatDate(String date) throws ParseException {
		return new SimpleDateFormat(ValidationConstants.getAllowedDateFormat()).parse(date);
	}

	// Date to Calendar format and set defaulkt time to midnight
	public Calendar dateToCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		return c;
	}

	// Function to add days
	public Date addDays(int days, Date toDate) throws ParseException {
		Calendar c = dateToCalendar(toDate);
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

	// Prepocess the bookingDates as set.contains cannot find duplicates because of
	// object equality rule
	public Set<String> bookingDatesToStringSet(Set<BookingDate> bookingDates) {
		Set<String> bookingDatesInStringFormat = new HashSet<>();
		for (BookingDate bookingDate : bookingDates) {
			bookingDatesInStringFormat.add(formatDate(bookingDate.getBookingDate()));
		}
		return bookingDatesInStringFormat;
	}

	// Get range between two dates
	public DateRange getDateRange(Set<BookingDate> reservations) {
		List<BookingDate> list = new ArrayList<BookingDate>(reservations);
		Collections.sort(list);
		return new DateRange(list.get(0).getBookingDate(),
				list.get(list.size() - 1).getBookingDate());
	}

	// Calculate difference in days between two dates
	public int dateDifference(Date endDate, Date startDate) {
		Calendar endDateCalendar   = dateToCalendar(endDate);
		Calendar startDateCalendar = dateToCalendar(startDate);
		return Math.round(
				(float) (endDateCalendar.getTimeInMillis() - startDateCalendar.getTimeInMillis())
						/ (24 * 60 * 60 * 1000));
	}

	// Check if start date is valid, start date should be one day ahead of today and
	// within one month in future
	public boolean hasValidStartDate(Date startDate, Date endDate) {
		int startDateFromNow = dateDifference(startDate, new Date());
		int endDateFromNow   = dateDifference(endDate, new Date());
		int numOfBookings    = dateDifference(endDate, startDate);
		return startDateFromNow >= 1 && endDateFromNow <= 30 && numOfBookings <= 3;
	}
}
