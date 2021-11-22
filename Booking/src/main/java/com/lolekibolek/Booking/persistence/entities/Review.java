package com.lolekibolek.Booking.persistence.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Review {

	@Id
	//private UUID reservationId = reservation.getId();
	
	@OneToOne
	@JoinColumn(name = "id")
	private Reservation reservation;
	
	private int cleanessRating;
	private int comfortRating;
	private int locationRating;
	private int hostRating;
	private int valueForMoneyRating;
	private float averageRating;
	private String review;
	
}
