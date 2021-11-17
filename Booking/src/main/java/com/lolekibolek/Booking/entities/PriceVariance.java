package com.lolekibolek.Booking.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PriceVariance {

	@Id
	private UUID id;
	private UUID apartmentId;
	private String name;
	private Date startDate;
	private Date endDate;
	private int percentage;
	
	public PriceVariance(UUID id, UUID apartmentId, String name, Date startDate, Date endDate, int percentage) {
		this.id = id;
		this.apartmentId = apartmentId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.percentage = percentage;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(UUID apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
}