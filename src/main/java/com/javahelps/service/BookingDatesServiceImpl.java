package com.javahelps.service;

import java.text.ParseException;

import java.util.Date;
import java.util.Set;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javahelps.errorhandling.Constants;
import com.javahelps.errorhandling.UserServiceException;
import com.javahelps.restservice.entity.Booking;
import com.javahelps.restservice.entity.BookingDate;
import com.javahelps.restservice.repository.BookingDateRepository;
import com.javahelps.restservice.repository.BookingRepository;

@Service
public class BookingDatesServiceImpl extends DateUtilImpl
		implements BookingDatesService {

	public JSONArray getAvailableDates(Set<BookingDate> bookedDates, Date startDate,
			Date endDate) throws ParseException {

		JSONArray   vacancies        = new JSONArray();
		Date        today            = new Date();
		Set<String> bookingRefactors = bookingDatesToStringSet(bookedDates);

		if (!bookingRefactors.contains(formatDate(startDate))) vacancies.add(formatDate(startDate));
		Date tempStartDate = startDate;
		while (tempStartDate.compareTo(endDate) < 0) {
			String newDate = formatDate(addDays(1, tempStartDate));
			bookingRefactors.contains(newDate);
			tempStartDate = addDays(1, tempStartDate);
			if (bookingRefactors.contains(newDate))
				continue;
			vacancies.add(newDate);
		}
		return vacancies;
	}

	public boolean hasExpiredBookingDates(Booking booking) throws UserServiceException {
		Set<BookingDate> bookingDates = booking.getBookingDates();
		for (BookingDate bookingDate : bookingDates)
			if (dateDifference(bookingDate.getBookingDate(), new Date()) < 0)
				return true;

		return false;

	}

	public boolean canWithdrawBooking(Booking booking) throws UserServiceException {
		if (booking == null)
			throw new UserServiceException(Constants.RESERVATION_NOT_FOUND,
					Constants.RESERVATION_NOT_FOUND_STATUS);

		if (hasExpiredBookingDates(booking))
			throw new UserServiceException(Constants.RESERVATION_EXPIRED,
					Constants.RESERVATION_EXPIRED_STATUS);
		
		return true;
		
	}
}
