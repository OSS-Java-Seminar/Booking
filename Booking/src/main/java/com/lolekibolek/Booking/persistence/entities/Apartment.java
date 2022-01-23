package com.lolekibolek.Booking.persistence.entities;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Apartment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apartment")
	private Set<PriceVariance> priceVariances;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apartment")
	private Set<Reservation> reservations;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn (name = "owner")
	private User owner;
	
	@Column(length = 50, nullable = false)
	private String country;
	
	@Column(length = 50, nullable = false)
	private String city;
	
	@Column(length = 100, nullable = false)
	private String address;
	
	@Column(nullable = false)
	private float pricePerNight;
	
	@Column(nullable = false)
	private int capacity;
	
	@Column(nullable = false)
	private float size;
	
	@Column(nullable = false)
	private int bedroomNumber;
	
	@Column(nullable = false, length = 1500)
	private String description;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean petsAllowed;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean smokingAllowed;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean disabledAccessible;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean freeCancellation;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean balcony;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean kitchen;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean parking;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean seaView;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean pool;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean jacuzzi;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean iron;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean washingMachine;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean ac;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean heating;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean wifi;
	
}
