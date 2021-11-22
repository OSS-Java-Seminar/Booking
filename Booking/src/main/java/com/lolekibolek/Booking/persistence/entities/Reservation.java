package com.lolekibolek.Booking.persistence.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Reservation {

	@Id
	private UUID id;
	
	@ManyToOne
	@JoinColumn (name = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn (name = "id")
	private Apartment apartment;
	
	private String checkInDate;
	private String checkOutDate;
	private float totalPrice;
	private String reservationStatus;
	private String paymentStatus;
	
}
