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

	public static final String FIND_ALL_BOOKINGS= "SELECT BOOKING_DATE FROM booking_date";
	public static final String FIND_BOOKED_DATES = "SELECT r FROM BookingDate r WHERE r.bookingDate >= ?1 and r.bookingDate <= ?2";
	public static final String FIND_VACANT_DATE = "SELECT r FROM BookingDate r WHERE r.bookingDate = ?1";

	@Query(value = FIND_ALL_BOOKINGS, nativeQuery = true)
	public Set<BookingDate> findCampsiteVacancy();

	@Query(value = FIND_BOOKED_DATES)
	public List<BookingDate> getBooking(Date startDate, Date endDate);
	
	@Query(value = FIND_VACANT_DATE)
	public BookingDate getBooking(Date startDate);

}
