package com.lolekibolek.Booking.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Review {

	@Id
	//private UUID reservationId = reservation.getId();
	
	@OneToOne
	@JoinColumn(name = "id")
	private Reservation reservation;
	
	private int cleanessRating;
	private int comfortRating;
	//....Rating
	private float averageRating;
	private String review;
	
	public Review(int cleanessRating, int comfortRating, float averageRating, String review) {
		this.cleanessRating = cleanessRating;
		this.comfortRating = comfortRating;
		this.averageRating = averageRating;
		this.review = review;
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
