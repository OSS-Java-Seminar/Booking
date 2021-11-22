package com.lolekibolek.Booking.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
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
	
	public Reservation(UUID id, String checkInDate, String checkOutDate, float totalPrice, String reservationStatus, 
			String paymentStatus) {
		this.id = id;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalPrice = totalPrice;
		this.reservationStatus = reservationStatus;
		this.paymentStatus = paymentStatus;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}	
	
}
