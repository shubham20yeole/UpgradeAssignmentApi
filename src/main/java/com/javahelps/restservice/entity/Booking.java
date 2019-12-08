package com.javahelps.restservice.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Booking implements Serializable{
	private static final long serialVersionUID = -6790693372846798580L;
	
	public Booking() {
		this.setBookingId(UUID.randomUUID().toString());
	}
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "BOOKING_ID", unique = true, length = 255)
	private String bookingId;

	@Column(name = "FULL_NAME", updatable = false, nullable = false)
	private String fullname;

	@Column(name = "EMAIL", updatable = false, nullable = false)
	private String email;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	private Set<BookingDate> bookingDates;

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String userId) {
		this.bookingId = userId;
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<BookingDate> getBookingDates() {
		return this.bookingDates;
	}

	public void setBookingDates(Set<BookingDate> bookingDates) {
		this.bookingDates = bookingDates;
	}
	
}
