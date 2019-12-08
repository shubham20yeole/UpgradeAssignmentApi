package com.javahelps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;

import com.javahelps.errorhandling.Constants;
import com.javahelps.restservice.entity.BookingDate;

public class DateUtilImpl implements DateUtil {
	public String formatDate(Date date) {
		return new SimpleDateFormat(Constants.ALLOWED_DATE_FORMAT).format(date);
	}

	public Date formatDate(String date) throws ParseException {
		return new SimpleDateFormat(Constants.ALLOWED_DATE_FORMAT).parse(date);
	}

	public Calendar dateToCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 12);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c;
	}

	public Date addDays(int days, Date toDate) throws ParseException {
		Calendar c = dateToCalendar(toDate);
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

	public Set<String> bookingDatesToStringSet(Set<BookingDate> bookingDates) {
		Set<String> bookingDatesInStringFormat = new HashSet<>();
		for (BookingDate bookingDate : bookingDates) {
			bookingDatesInStringFormat.add(formatDate(bookingDate.getBookingDate()));
		}
		return bookingDatesInStringFormat;
	}

	public DateRange getDateRange(Set<BookingDate> reservations) {
		List<BookingDate> list = new ArrayList<BookingDate>(reservations);
		Collections.sort(list);
		return new DateRange(list.get(0).getBookingDate(),
				list.get(list.size() - 1).getBookingDate());
	}

	public int dateDifference(Date endDate, Date startDate) {
		Calendar endDateCalendar  = dateToCalendar(endDate);
		Calendar startDateCalendar = dateToCalendar	(startDate);
		return Math.round(
				(float) (endDateCalendar.getTimeInMillis() - startDateCalendar.getTimeInMillis()) / (24 * 60 * 60 * 1000));
	}

	public boolean hasValidStartDate(Date startDate, Date endDate) {
		int startDateFromNow = dateDifference(startDate, new Date());
		int endDateFromNow   = dateDifference(endDate, new Date());
		int numOfBookings = dateDifference(endDate, startDate);
		return startDateFromNow >= 1 && endDateFromNow <= 30 && numOfBookings <= 3;
	}
}
