package com.javahelps.restservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.javahelps.restservice.entity.Reservation;
import com.javahelps.restservice.entity.User;

@RestResource(exported = false)
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
	public static final ReservationRepository ReservationDao = null;
}
