package com.lolekibolek.Booking.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Apartment {

	@Id
	private UUID id;
	private String name;
	private String owner;
	private String location;
	private float pricePerNight;
	private String description;
	private String additionailInfo;
	
	public Apartment(UUID id, String name, String owner, String location, float pricePerNight, String description, 
			String additionailInfo) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.location = location;
		this.pricePerNight = pricePerNight;
		this.description = description;
		this.additionailInfo = additionailInfo;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdditionailInfo() {
		return additionailInfo;
	}

	public void setAdditionailInfo(String additionailInfo) {
		this.additionailInfo = additionailInfo;
	}
	
}
