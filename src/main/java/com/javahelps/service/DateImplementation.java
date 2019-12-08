package com.javahelps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;

import com.javahelps.restservice.entity.Reservation;

public class DateImplementation extends DateIml{

	public String dateToString(Date date) {
		return new SimpleDateFormat("MM-dd-yyyy").format(date);
	}
	
	public Calendar getCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
	    c.set(Calendar.HOUR_OF_DAY, 12);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    return c;
	}

	public Calendar addDays(int days, Date toDate) throws ParseException {
		Calendar c = getCalendar(toDate);
		c.add(Calendar.DAY_OF_MONTH, days);  
		return c;
	}
	
	public Date stringToDate(Date date) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTime();
	}
	
	
	public List<String> invalidDates(Set<Object> reservations, Set<Reservation> newReservations) throws ParseException {
		Set<String> reservationDates = preProcessReservationSet(reservations);
		List<String> invalidDates = new LinkedList<>();
		for (Reservation newRes: newReservations) {
			String newDate = dateToString(newRes.getReservationDate());
			if (reservationDates.contains(newDate)) {
				invalidDates.add(newDate);
			}
		}
		return invalidDates;
	}

	public JSONArray getVacancies(Set<Object> reservations) throws ParseException {
		JSONArray vacancies = new JSONArray();
		Date today = new Date();
		Set<String> reservationsTemop = preProcessReservationSet(reservations);
		for (int dayCount = 0; dayCount <= 365; dayCount++) {
			String newDate = dateToString(addDays(dayCount, today).getTime());
			if (reservationsTemop.contains(newDate)) continue;
			vacancies.add(newDate);
		}
		return vacancies;
	}
	
	public Set<String> preProcessReservationSet(Set<Object> reservations) {
		Set<String> reservationsTemop = new HashSet<>();
		for (Object res: reservations) {
			reservationsTemop.add(dateToString((Date)res));
		}
		return reservationsTemop;
	}
	
	public DateRange getResDateRange(Set<Reservation> reservations) {
		List<Reservation> list = new ArrayList<Reservation>(reservations);
		Collections.sort(list);
		return new DateRange(list.get(0).getReservationDate(), list.get(list.size()-1).getReservationDate());
	}

}

