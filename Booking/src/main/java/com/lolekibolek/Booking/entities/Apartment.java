package com.lolekibolek.Booking.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Apartment {

	@Id
	private UUID id;
	
	@OneToMany
	private List<PriceVariance> priceVariance;
	
	@OneToMany
	private List<Reservation> reservation;
	
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
	
	public Apartment(UUID id, List<PriceVariance> priceVariance, List<Reservation> reservation, String name, User owner,
			String country, String city, String address, float pricePerNight, int capacity, float size,
			int bedroomNumber, String description, boolean petsAllowed, boolean smokingAllowed,
			boolean disabledAccessible, boolean freeCancellation, boolean balcony, boolean kitchen, boolean parking,
			boolean seaView, boolean pool, boolean jacuzzi, boolean iron, boolean washingMachine, boolean aC,
			boolean heating, boolean wifi) {
		super();
		this.id = id;
		this.priceVariance = priceVariance;
		this.reservation = reservation;
		this.name = name;
		this.owner = owner;
		this.country = country;
		this.city = city;
		this.address = address;
		this.pricePerNight = pricePerNight;
		this.capacity = capacity;
		this.size = size;
		this.bedroomNumber = bedroomNumber;
		this.description = description;
		this.petsAllowed = petsAllowed;
		this.smokingAllowed = smokingAllowed;
		this.disabledAccessible = disabledAccessible;
		this.freeCancellation = freeCancellation;
		this.balcony = balcony;
		this.kitchen = kitchen;
		this.parking = parking;
		this.seaView = seaView;
		this.pool = pool;
		this.jacuzzi = jacuzzi;
		this.iron = iron;
		this.washingMachine = washingMachine;
		AC = aC;
		this.heating = heating;
		this.wifi = wifi;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public List<PriceVariance> getPriceVariance() {
		return priceVariance;
	}
	public void setPriceVariance(List<PriceVariance> priceVariance) {
		this.priceVariance = priceVariance;
	}
	public List<Reservation> getReservation() {
		return reservation;
	}
	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
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
	public float getPricePerNight() {
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
	public boolean isFreeCancellation() {
		return freeCancellation;
	}
	public void setFreeCancellation(boolean freeCancellation) {
		this.freeCancellation = freeCancellation;
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
	public boolean isAC() {
		return AC;
	}
	public void setAC(boolean aC) {
		AC = aC;
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
	
	

	
	
}
