package com.javahelps.restservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.javahelps.restservice.entity.Booking;

@RestResource(exported = false)
@Repository
public interface BookingRepository
		extends JpaRepository<Booking, String> {
	public static final BookingRepository UserDao = null;
}
