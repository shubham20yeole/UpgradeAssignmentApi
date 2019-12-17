package com.javahelps.restservice.bookingValidations;

import java.util.AbstractMap;
import java.util.List;
import java.util.Set;

import com.javahelps.restservice.entity.BookingDate;
import com.javahelps.restservice.service.DateRange;

public class Validations extends ValidationConstants {

	private String  bookingError     = null;
	private Integer bookingErrorCode = null;

	public Validations(Set<BookingDate> bookingDates, DateRange range,
			List<BookingDate> conflicts, BookingDate reserved) {
		if (bookingDates.size() == 0) {
			this.bookingError     = DATES_NOT_FOUND;
			this.bookingErrorCode = DATES_NOT_FOUND_STATUS;
		} else if (bookingDates.size() > 3) {
			this.bookingError     = DATES_LONG_DURATION;
			this.bookingErrorCode = DATES_LONG_DURATION_STATUS;
		} else if (range.isSingleDayReservation() && reserved != null) {
			this.bookingError     = DATES_UNAVAILABLE;
			this.bookingErrorCode = DATES_UNAVAILABLE_STATUS;
		} else if (!range.hasValidStartDate()) {
			this.bookingError     = DATES_INVALID;
			this.bookingErrorCode = DATES_INVALID_STATUS;
		} else if (conflicts.size() > 0) {
			this.bookingError     = DATES_UNAVAILABLE;
			this.bookingErrorCode = DATES_UNAVAILABLE_STATUS;
		} else if (!range.isThreeDaysReservation()) {
			this.bookingError     = DATES_MAX_DURATION_EXCEED;
			this.bookingErrorCode = 403;
		}
	}

	public boolean isValid() {
		return this.bookingError == null && this.bookingErrorCode == null;
	}

	public AbstractMap.Entry<String, Integer> getValidation() {
		return new AbstractMap.SimpleEntry<>(this.bookingError, this.bookingErrorCode);
	}

}
