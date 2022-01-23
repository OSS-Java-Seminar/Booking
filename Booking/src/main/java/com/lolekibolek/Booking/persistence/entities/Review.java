package com.lolekibolek.Booking.persistence.entities;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Entity
@Data
public class Review {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
	@Autowired
	private Reservation reservation;
	
	@Column(nullable = false)
	private int cleanessRating;
	
	@Column(nullable = false)
	private int comfortRating;
	
	@Column(nullable = false)
	private int locationRating;
	
	@Column(nullable = false)
	private int hostRating;
	
	@Column(nullable = false)
	private int valueForMoneyRating;
	
	@Column(nullable = false)
	private float averageRating;
	
	@Column(length = 500)
	private String review;
	
}
