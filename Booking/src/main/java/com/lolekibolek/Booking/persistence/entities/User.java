package com.lolekibolek.Booking.persistence.entities;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	private UUID id;
	
	@OneToMany
	private Set<Apartment> apartments;
	
	@OneToMany
	private Set<Reservation> reservations;
	
	private String role;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNumber;
	private String safeQuestion;
	private String safeAnswer;
	
}
