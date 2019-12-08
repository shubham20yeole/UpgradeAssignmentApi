package com.javahelps.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.javahelps.restservice.entity.BookingDate;

public interface DateUtil {
	public String formatDate(Date date);

	public Date formatDate(String date) throws ParseException;

	public Calendar dateToCalendar(Date date);

	public Date addDays(int days, Date toDate) throws ParseException;

	public Set<String> bookingDatesToStringSet(Set<BookingDate> bookingDates);

	public DateRange getDateRange(Set<BookingDate> reservations);

	public int dateDifference(Date endDate, Date startDate);

	public boolean hasValidStartDate(Date startDate, Date endDate);
}
