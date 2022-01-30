package com.lolekibolek.Booking.persistence.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReviewDto {

	@NotNull
    @NotEmpty
    private int cleanessRating;
    
    @NotNull
    @NotEmpty
	private int comfortRating;
    
    @NotNull
    @NotEmpty
	private int locationRating;
    
    @NotNull
    @NotEmpty
    private int hostRating;
    
    @NotNull
    @NotEmpty    
    private int valueForMoneyRating;
    
    @NotNull
    @NotEmpty  
    private float averageRating;
    
    @NotNull
    @NotEmpty  
	private String review;

	public ReviewDto(@NotNull @NotEmpty int cleanessRating, @NotNull @NotEmpty int comfortRating,
			@NotNull @NotEmpty int locationRating, @NotNull @NotEmpty int hostRating,
			@NotNull @NotEmpty int valueForMoneyRating, @NotNull @NotEmpty float averageRating,
			@NotNull @NotEmpty String review) {
		super();
		this.cleanessRating = cleanessRating;
		this.comfortRating = comfortRating;
		this.locationRating = locationRating;
		this.hostRating = hostRating;
		this.valueForMoneyRating = valueForMoneyRating;
		this.averageRating = averageRating;
		this.review = review;
	}

	public ReviewDto() {
		super();
		// TODO Auto-generated constructor stub
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
