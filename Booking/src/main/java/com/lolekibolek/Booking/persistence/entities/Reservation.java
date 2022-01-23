package com.lolekibolek.Booking.persistence.entities;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

enum ReservationStatus {
	  BOOKED,
	  CHECKEDIN,
	  CHECKEDOUT,
	  CANCELED
	}

@Entity
@Data
public class Reservation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn (name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn (name = "apartment_id")
	private Apartment apartment;
	
	@Column(nullable = false)
	private Date checkInDate;
	
	@Column(nullable = false)
	private Date checkOutDate;
	
	@Column(nullable = false)
	private float totalPrice;
	
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ReservationStatus reservationStatus;
	
}
