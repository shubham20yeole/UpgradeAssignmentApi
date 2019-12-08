package com.javahelps.restservice.repository;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javahelps.restservice.entity.Reservation;
import com.javahelps.restservice.entity.User;
import com.javahelps.restservice.repository.ReservationRepository;
import com.javahelps.restservice.repository.UserRepository;
import org.hibernate.Session;
import javassist.tools.web.BadHttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.javahelps.restservice.entity.Reservation;
import com.javahelps.restservice.entity.User;

@RestResource(exported = false)
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
	public static final ReservationRepository ReservationDao = null;

	public static final String FIND_RESERVED_DATES = "SELECT RESERVATION_DATE FROM reservation";

	@Query(value = FIND_RESERVED_DATES, nativeQuery = true)
	public Set<Object> findCampsiteVacancy();
	
	@Query("SELECT r FROM Reservation r WHERE r.reservationDate >= ?1 and r.reservationDate <= ?2")
	public List<Reservation> isVcancyAvailable(Date startDate, Date endDate);
	
}
