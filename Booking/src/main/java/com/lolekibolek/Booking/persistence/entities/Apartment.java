package com.lolekibolek.Booking.persistence.entities;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Apartment {

	@Id
	private UUID id;
	
	@OneToMany
	private Set<PriceVariance> priceVariances;
	
	@OneToMany
	private Set<Reservation> reservations;
	
	private String name;
	
	@ManyToOne
	@JoinColumn (name = "id")
	private User owner;
	
	private String country;
	private String city;
	private String address;
	private float pricePerNight;
	private int capacity;
	private float size;
	private int bedroomNumber;
	private String description;
	private boolean petsAllowed;
	private boolean smokingAllowed;
	private boolean disabledAccessible;
	private boolean freeCancellation;
	private boolean balcony;
	private boolean kitchen;
	private boolean parking;
	private boolean seaView;
	private boolean pool;
	private boolean jacuzzi;
	private boolean iron;
	private boolean washingMachine;
	private boolean AC;
	private boolean heating;
	private boolean wifi;
	
}
