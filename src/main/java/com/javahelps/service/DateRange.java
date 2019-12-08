package com.javahelps.service;

import java.util.Calendar;
import java.util.Date;

public class DateRange {
	private Date startDate;
	private Date endDate;
	DateImplementation dateUtils = new DateImplementation();

	public DateRange(Date startDate, Date endDate) {
		startDate = dateUtils.getCalendar(startDate).getTime();
		this.startDate = startDate;
		this.endDate = endDate == null ? startDate : dateUtils.getCalendar(endDate).getTime();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = dateUtils.getCalendar(startDate).getTime();
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = dateUtils.getCalendar(endDate).getTime();
	}

	public boolean isSingleDayReservation() {
		return this.getEndDate().equals(this.startDate);
	}
	
	public int dateDifference(Date maxDate, Date minDate) {
		Calendar c = dateUtils.getCalendar(minDate);
		Calendar c2 = dateUtils.getCalendar(maxDate);
		return Math.round((float) (c2.getTimeInMillis() - c.getTimeInMillis()) / (24 * 60 * 60 * 1000));
	}

	
	public boolean hasValidStartDate() {
		int start = dateDifference(this.getStartDate(), new Date());
		int end = dateDifference(this.getEndDate(), new Date());
		return dateDifference(this.getStartDate(), new Date()) >= 1 && dateDifference(this.getEndDate(), new Date()) <= 30;
	}
	
	public boolean isThreeDaysReservation() {
		return dateDifference(this.getEndDate(), this.getStartDate()) <= 3;
	}
	
}
