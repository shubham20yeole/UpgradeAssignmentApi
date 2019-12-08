package com.javahelps.service;

import java.util.Calendar;
import java.util.Date;

public class DateRange extends DateUtilImpl{
	private Date       startDate;
	private Date       endDate;

	public DateRange(Date startDate, Date endDate) {
		startDate      = dateToCalendar(startDate).getTime();
		this.startDate = startDate;
		this.endDate   = endDate == null ? startDate
				: dateToCalendar(endDate).getTime();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = dateToCalendar(startDate).getTime();
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = dateToCalendar(endDate).getTime();
	}

	public boolean isSingleDayReservation() {
		return this.getEndDate().equals(this.startDate);
	}

	public boolean hasValidStartDate() {
		return hasValidStartDate(this.getStartDate(), this.getEndDate());
	}

	public boolean isThreeDaysReservation() {
		return dateDifference(this.getEndDate(), this.getStartDate()) <= 3;
	}

}
