package com.javahelps.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import org.json.simple.JSONArray;

import com.javahelps.restservice.entity.BookingDate;

public class BookingDatesServiceImpl extends DateUtilImpl
		implements BookingDatesService {
	public JSONArray getAvailableDates(Set<BookingDate> bookedDates, Date startDate,
			Date endDate) throws ParseException {

		JSONArray   vacancies        = new JSONArray();
		Date        today            = new Date();
		Set<String> bookingRefactors = bookingDatesToStringSet(bookedDates);

		Date tempStartDate = startDate;
		while (tempStartDate.compareTo(endDate) <= 0) {
			String newDate = formatDate(addDays(1, tempStartDate));
			bookingRefactors.contains(newDate);
			tempStartDate = addDays(1, tempStartDate);
			if (bookingRefactors.contains(newDate))
				continue;
			vacancies.add(newDate);
		}
		return vacancies;
	}
}
