package com.javahelps.errorhandling;

public class Constants {
	public final static int INVALID_PORT_CONFIGURATION = 500;

	public final static String DATES_UNAVAILABLE        = "Campsite not available for provided dates, please try again.";
	public final static int    DATES_UNAVAILABLE_STATUS = 403;

	public final static String DATES_NOT_FOUND        = "Reservation date/s required.";
	public final static int    DATES_NOT_FOUND_STATUS = 422;

	public final static String DATES_INVALID        = "The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance.";
	public final static int    DATES_INVALID_STATUS = 416;

	public final static String DATES_MAX_DURATION_EXCEED        = "The campsite can be reserved for only three days.";
	public final static int    DATES_MAX_DURATION_EXCEED_STATUS = 416;

	public final static String RESERVATION_EXPIRED        = "Reservations in past. Can not update entity";
	public final static int    RESERVATION_EXPIRED_STATUS = 500;
	
	public final static String INVALID_START_END_DATES        = "End date has to be ahead of start date, Invalid input.";
	public final static int    INVALID_START_END_DATES_STATUS = 416;

	public final static String ALLOWED_DATE_FORMAT = "yyyy-MM-dd";
}
