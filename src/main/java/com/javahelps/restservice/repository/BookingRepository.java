package com.javahelps.restservice.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
	
	
	public static void updateBooking(Booking booking) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("upgradeassignment");
	  EntityManager entitymanager = emfactory.createEntityManager();
	  entitymanager.getTransaction().begin();
	  entitymanager.persist(booking);
	  entitymanager.getTransaction().commit();
	  entitymanager.close();
	  emfactory.close();
	}
	
}
