package com.javahelps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;

public class DateImplementation extends DateIml{

	public String dateToString(Date date) {
		return new SimpleDateFormat("MM-dd-yyyy").format(date);
	}

	public String stringToDate(String date) {
		return new SimpleDateFormat("MM-dd-yyyy").format(date);
	}

	public String addDays(int days, Date toDate) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		c.add(Calendar.DAY_OF_MONTH, days);  
		return dateToString(c.getTime());
	}

	public JSONArray getVacancies(Set<Object> reservations) throws ParseException {
		JSONArray vacancies = new JSONArray();
		Date today = new Date();
		
		Set<String> reservationsTemop = new HashSet<>();
		for (Object res: reservations) {
			reservationsTemop.add(dateToString((Date)res));
		}
		
		for (int dayCount = 0; dayCount <= 365; dayCount++) {
			String newDate = addDays(dayCount, today);
			if (reservationsTemop.contains(newDate)) continue;
			vacancies.add(newDate);
		}
		return vacancies;
	}

}
