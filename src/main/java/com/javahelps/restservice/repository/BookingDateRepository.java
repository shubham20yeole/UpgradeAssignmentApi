package com.javahelps.restservice.repository;

import java.util.*;

import org.springframework.data.jpa.repository.Query;
import com.javahelps.restservice.entity.BookingDate;
import com.javahelps.restservice.repository.BookingDateRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@RestResource(exported = false)
@Repository
public interface BookingDateRepository extends JpaRepository<BookingDate, String> {
	public static final BookingDateRepository ReservationDao = null;

	public static final String FIND_BOOKED_DATES = "SELECT BOOKING_DATE FROM booking_date";
	public static final String FIND_VACANT_DATES = "SELECT r FROM BookingDate r WHERE r.bookingDate >= ?1 and r.bookingDate <= ?2";

	@Query(value = FIND_BOOKED_DATES, nativeQuery = true)
	public Set<BookingDate> findCampsiteVacancy();

	@Query(value = FIND_VACANT_DATES)
	public List<BookingDate> getBookings(Date startDate, Date endDate);

}
