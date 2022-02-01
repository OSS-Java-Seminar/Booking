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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Apartment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apartment")
	private Set<Reservation> reservations;
	
	@NotEmpty(message = "Apartment's name cannot be empty.")
	@Column(length = 100, nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn (name = "owner")
	private User owner;
	
	@NotEmpty(message = "Country cannot be empty.")
	@Column(length = 50, nullable = false)
	private String country;
	
	@NotEmpty(message = "City cannot be empty.")
	@Column(length = 50, nullable = false)
	private String city;
	
	@NotEmpty(message = "Address cannot be empty.")
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
	
	@Column(nullable = false)
	private Double rating = 0.0;

	@Column(nullable = false, length = 1500)
	private String description;
	
	@Column(nullable = false, length = 5000)
	private String picture;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean petsAllowed;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean smokingAllowed;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean disabledAccessible;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public int getBedroomNumber() {
		return bedroomNumber;
	}

	public void setBedroomNumber(int bedroomNumber) {
		this.bedroomNumber = bedroomNumber;
	}

	public String getDescription() {
		return description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPetsAllowed() {
		return petsAllowed;
	}

	public void setPetsAllowed(boolean petsAllowed) {
		this.petsAllowed = petsAllowed;
	}

	public boolean isSmokingAllowed() {
		return smokingAllowed;
	}

	public void setSmokingAllowed(boolean smokingAllowed) {
		this.smokingAllowed = smokingAllowed;
	}

	public boolean isDisabledAccessible() {
		return disabledAccessible;
	}

	public void setDisabledAccessible(boolean disabledAccessible) {
		this.disabledAccessible = disabledAccessible;
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public boolean isKitchen() {
		return kitchen;
	}

	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public boolean isSeaView() {
		return seaView;
	}

	public void setSeaView(boolean seaView) {
		this.seaView = seaView;
	}

	public boolean isPool() {
		return pool;
	}

	public void setPool(boolean pool) {
		this.pool = pool;
	}

	public boolean isJacuzzi() {
		return jacuzzi;
	}

	public void setJacuzzi(boolean jacuzzi) {
		this.jacuzzi = jacuzzi;
	}

	public boolean isIron() {
		return iron;
	}

	public void setIron(boolean iron) {
		this.iron = iron;
	}

	public boolean isWashingMachine() {
		return washingMachine;
	}

	public void setWashingMachine(boolean washingMachine) {
		this.washingMachine = washingMachine;
	}

	public boolean isAc() {
		return ac;
	}

	public void setAc(boolean ac) {
		this.ac = ac;
	}

	public boolean isHeating() {
		return heating;
	}

	public void setHeating(boolean heating) {
		this.heating = heating;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}
	
	public Apartment(int id, Set<Reservation> reservations, String name, User owner,
			String country, String city, String address, float pricePerNight, int capacity, float size,
			int bedroomNumber, Double rating, String description, String picture, boolean petsAllowed,
			boolean smokingAllowed, boolean disabledAccessible, boolean balcony, boolean kitchen, boolean parking,
			boolean seaView, boolean pool, boolean jacuzzi, boolean iron, boolean washingMachine, boolean ac,
			boolean heating, boolean wifi) {
		super();
		this.id = id;
		this.reservations = reservations;
		this.name = name;
		this.owner = owner;
		this.country = country;
		this.city = city;
		this.address = address;
		this.pricePerNight = pricePerNight;
		this.capacity = capacity;
		this.size = size;
		this.bedroomNumber = bedroomNumber;
		this.rating = rating;
		this.description = description;
		this.picture = picture;
		this.petsAllowed = petsAllowed;
		this.smokingAllowed = smokingAllowed;
		this.disabledAccessible = disabledAccessible;
		this.balcony = balcony;
		this.kitchen = kitchen;
		this.parking = parking;
		this.seaView = seaView;
		this.pool = pool;
		this.jacuzzi = jacuzzi;
		this.iron = iron;
		this.washingMachine = washingMachine;
		this.ac = ac;
		this.heating = heating;
		this.wifi = wifi;
	}

	public Apartment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
}
