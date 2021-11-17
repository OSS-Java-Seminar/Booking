package com.lolekibolek.Booking.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Review {

	@Id
	private UUID reservationId;
	private int cleanessRating;
	private int comfortRating;
	//....Rating
	private float averageRating;
	private String review;
	
	public Review(UUID reservationId, int cleanessRating, int comfortRating, float averageRating, String review) {
		super();
		this.reservationId = reservationId;
		this.cleanessRating = cleanessRating;
		this.comfortRating = comfortRating;
		this.averageRating = averageRating;
		this.review = review;
	}

	public UUID getReservationId() {
		return reservationId;
	}

	public void setReservationId(UUID reservationId) {
		this.reservationId = reservationId;
	}

	public int getCleanessRating() {
		return cleanessRating;
	}

	public void setCleanessRating(int cleanessRating) {
		this.cleanessRating = cleanessRating;
	}

	public int getComfortRating() {
		return comfortRating;
	}

	public void setComfortRating(int comfortRating) {
		this.comfortRating = comfortRating;
	}

	public float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
}
