package com.javahelps.restservice.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.javahelps.service.DateImplementation;

@Entity
public class Reservation implements Comparable<Reservation> {
	
	public Reservation() {
		this.setReservationId(UUID.randomUUID().toString());
	}
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "RESERVATION_ID", unique = true, length = 255)
	private String reservationId;
	
	@Column(name = "RESERVATION_DATE", unique = true, nullable = false)
    private Date reservationDate;

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	
	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(reservationDate);
	    c.set(Calendar.HOUR_OF_DAY, 12);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
		this.reservationDate = c.getTime();
	}
	
	 @Override
	  public int compareTo(Reservation o) {
	    return this.getReservationDate().compareTo(o.getReservationDate());
	  }
	
	@ManyToOne
    private User user;

}
