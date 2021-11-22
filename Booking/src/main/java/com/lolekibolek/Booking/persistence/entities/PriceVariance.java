package com.lolekibolek.Booking.persistence.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class PriceVariance {

	@Id
	private UUID id;
	
	@ManyToOne
	@JoinColumn (name = "id")
	private Apartment apartment;
	
	private String name;
	private Date startDate;
	private Date endDate;
	private float newPrice;

}
