package com.lolekibolek.Booking.persistence.entities;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Entity
@Data
public class Review {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
	@Autowired
	private Reservation reservation;
	
	@Column(nullable = false)
	private int cleanessRating;
	
	@Column(nullable = false)
	private int comfortRating;
	
	@Column(nullable = false)
	private int locationRating;
	
	@Column(nullable = false)
	private int hostRating;
	
	@Column(nullable = false)
	private int valueForMoneyRating;
	
	@Column(nullable = false)
	private float averageRating;
	
	@Column(length = 500)
	private String review;

	public Review(int id, Reservation reservation, int cleanessRating, int comfortRating, int locationRating,
			int hostRating, int valueForMoneyRating, float averageRating, String review) {
		super();
		this.id = id;
		this.reservation = reservation;
		this.cleanessRating = cleanessRating;
		this.comfortRating = comfortRating;
		this.locationRating = locationRating;
		this.hostRating = hostRating;
		this.valueForMoneyRating = valueForMoneyRating;
		this.averageRating = averageRating;
		this.review = review;
	}

	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
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

	public int getLocationRating() {
		return locationRating;
	}

	public void setLocationRating(int locationRating) {
		this.locationRating = locationRating;
	}

	public int getHostRating() {
		return hostRating;
	}

	public void setHostRating(int hostRating) {
		this.hostRating = hostRating;
	}

	public int getValueForMoneyRating() {
		return valueForMoneyRating;
	}

	public void setValueForMoneyRating(int valueForMoneyRating) {
		this.valueForMoneyRating = valueForMoneyRating;
	}

	public float getAverageRating() {
		int rating = 0;
		rating += this.cleanessRating;
		rating += this.comfortRating;
		rating += this.locationRating;
		rating += this.hostRating;
		rating += this.valueForMoneyRating;
		this.averageRating = rating/5;
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
