package com.javahelps.restservice.entity;

import java.io.Serializable;
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

@Entity
public class Reservation implements Serializable {
	
	private static final long serialVersionUID = -6790693372846798580L;

	public Reservation() {
		this.setReservationId(UUID.randomUUID().toString());
	}
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "RESERVATION_ID", unique = true, length = 255)
	private String reservationId;
	
	@Basic
	@Column(name = "RESERVATION_DATE")
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
		this.reservationDate = reservationDate;
	}
	
	@ManyToOne
    private User user;

}
