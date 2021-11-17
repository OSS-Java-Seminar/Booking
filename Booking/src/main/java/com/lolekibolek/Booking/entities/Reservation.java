package com.lolekibolek.Booking.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Reservation {

	@Id
	private UUID id;
	private UUID userId;
	private UUID apartmentId;
	private String checkInDate;
	private String checkOutDate;
	private float totalPrice;
	private String status;
	
	public Reservation(UUID id, UUID userId, UUID apartmentId, String checkInDate, String checkOutDate,
			float totalPrice, String status) {
		this.id = id;
		this.userId = userId;
		this.apartmentId = apartmentId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalPrice = totalPrice;
		this.status = status;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(UUID apartmentId) {
		this.apartmentId = apartmentId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
	
}
