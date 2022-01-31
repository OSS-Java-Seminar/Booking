package com.lolekibolek.Booking.persistence.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ApartmentDto {

	@NotNull
    @NotEmpty
    private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotNull
    @NotEmpty
    private String name;
	
    @NotNull
    @NotEmpty
    private String country;
    
    @NotNull
    @NotEmpty
	private String city;
    
    @NotNull
    @NotEmpty
	private String address;
    
    @NotNull
    @NotEmpty
	private float pricePerNight;
    
    @NotNull
    @NotEmpty
	private int capacity;
    
    @NotNull
    @NotEmpty
	private float size;
    
    @NotNull
    @NotEmpty
	private int bedroomNumber;
    
    @NotNull
    @NotEmpty
	private String description;
    
    @NotNull
    @NotEmpty
	private String picture;
    
    @NotNull
    @NotEmpty
	private boolean petsAllowed;
    
    @NotNull
    @NotEmpty
	private boolean smokingAllowed;
    
    @NotNull
    @NotEmpty
    private boolean disabledAccessible;
    
    @NotNull
    @NotEmpty
    private boolean balcony;
    
    @NotNull
    @NotEmpty
    private boolean kitchen;
    
    @NotNull
    @NotEmpty
    private boolean parking;
    
    @NotNull
    @NotEmpty
    private boolean seaView;
    
    @NotNull
    @NotEmpty
    private boolean pool;
    
    @NotNull
    @NotEmpty
    private boolean jacuzzi;
    
    @NotNull
    @NotEmpty
    private boolean iron;
    
    @NotNull
    @NotEmpty
    private boolean washingMachine;
    
    @NotNull
    @NotEmpty
    private boolean ac;
    
    @NotNull
    @NotEmpty
    private boolean heating;
    
    @NotNull
    @NotEmpty
    private boolean wifi;

	public ApartmentDto(@NotNull @NotEmpty String name, @NotNull @NotEmpty String country,
			@NotNull @NotEmpty String city, @NotNull @NotEmpty String address, @NotNull @NotEmpty float pricePerNight,
			@NotNull @NotEmpty int capacity, @NotNull @NotEmpty float size, @NotNull @NotEmpty int bedroomNumber,
			@NotNull @NotEmpty String description, @NotNull @NotEmpty String picture,
			@NotNull @NotEmpty boolean petsAllowed, @NotNull @NotEmpty boolean smokingAllowed,
			@NotNull @NotEmpty boolean disabledAccessible, @NotNull @NotEmpty boolean balcony,
			@NotNull @NotEmpty boolean kitchen, @NotNull @NotEmpty boolean parking, @NotNull @NotEmpty boolean seaView,
			@NotNull @NotEmpty boolean pool, @NotNull @NotEmpty boolean jacuzzi, @NotNull @NotEmpty boolean iron,
			@NotNull @NotEmpty boolean washingMachine, @NotNull @NotEmpty boolean ac,
			@NotNull @NotEmpty boolean heating, @NotNull @NotEmpty boolean wifi) {
		super();
		this.name = name;
		this.country = country;
		this.city = city;
		this.address = address;
		this.pricePerNight = pricePerNight;
		this.capacity = capacity;
		this.size = size;
		this.bedroomNumber = bedroomNumber;
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

	public ApartmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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
    
    
    
}
