package com.javahelps.restservice.bookingValidations;

public class ValidationConstants {

	protected final static String GENERIC_ERROR_MESSAGE        = "Something went wrong, we can not process this request.";
	protected final static int    GENERIC_ERROR_MESSAGE_STATUS = 500;

	protected final static String DATES_UNAVAILABLE        = "Campsite not available for provided dates, please try again.";
	protected final static int    DATES_UNAVAILABLE_STATUS = 403; // Forbidden

	protected final static String DATES_LONG_DURATION        = "Campsite can be reserved for max 3 days.";
	protected final static int    DATES_LONG_DURATION_STATUS = 403; // Forbidden

	protected final static String DATES_NOT_FOUND        = "Reservation date/s required.";
	protected final static int    DATES_NOT_FOUND_STATUS = 422; // Unprocessable Entity

	protected final static String DATES_INVALID        = "The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance.";
	protected final static int    DATES_INVALID_STATUS = 403; // Forbidden

	protected final static String DATES_MAX_DURATION_EXCEED        = "The campsite can be reserved for only three days.";
	protected final static int    DATES_MAX_DURATION_EXCEED_STATUS = 403; // Forbidden

	private final static String RESERVATION_EXPIRED        = "Reservations in past. Can not update/cancel entity.";
	private final static int    RESERVATION_EXPIRED_STATUS = 403; // Forbidden

	private final static String RESERVATION_NOT_FOUND        = "Invalid token: Reservation does not exist, please try again with correct booking token.";
	private final static int    RESERVATION_NOT_FOUND_STATUS = 404;

	protected final static String INVALID_START_END_DATES        = "End date has to be ahead of start date, Invalid input.";
	protected final static int    INVALID_START_END_DATES_STATUS = 403; // Forbidden

	protected final static String INVALID_NAME_OR_EMAIL        = "FULL_NAME/EMAIL may not be null or empty";
	protected final static int    INVALID_NAME_OR_EMAIL_STATUS = 403; // Forbidden

	private final static String ALLOWED_DATE_FORMAT = "yyyy-MM-dd";

	public static String getInvalidNameOrEmail() {
		return INVALID_NAME_OR_EMAIL;
	}

	public static int getInvalidNameOrEmailStatus() {
		return INVALID_NAME_OR_EMAIL_STATUS;
	}

	public static String getReservationExpired() {
		return RESERVATION_EXPIRED;
	}

	public static int getReservationExpiredStatus() {
		return RESERVATION_EXPIRED_STATUS;
	}

	public static String getAllowedDateFormat() {
		return ALLOWED_DATE_FORMAT;
	}

	public static int getReservationNotFoundStatus() {
		return RESERVATION_NOT_FOUND_STATUS;
	}

	public static String getReservationNotFound() {
		return RESERVATION_NOT_FOUND;
	}
}
