package com.javahelps.restservice.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Booking implements Serializable {
	private static final long serialVersionUID = -6790693372846798580L;

	public Booking() {
		this.setBookingId(UUID.randomUUID().toString());
	}

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "BOOKING_ID", unique = true, length = 255)
	private String bookingId;

	@Version
	@Column(name = "VERSION")
	private long version;
	
	
	@NotNull(message = "FULL_NAME may not be null")
	@NotEmpty(message = "FULL_NAME may not be empty")
	@Column(name = "FULL_NAME")
	private String fullname;

	@NotNull(message = "EMAIL may not be null")
	@NotEmpty(message = "EMAIL may not be empty")
	@Column(name = "EMAIL")
	private String email;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
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

	public void setBookingDates(
			Set<BookingDate> bookingDates) {
		this.bookingDates = bookingDates;
	}

}
