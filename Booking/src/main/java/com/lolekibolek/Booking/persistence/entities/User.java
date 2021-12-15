package com.lolekibolek.Booking.persistence.entities;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

enum Role {
	USER,
	OWNER
}

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Apartment> apartments;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Reservation> reservations;
	
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Role role;
	
	@Column(nullable = false, length = 30)
	private String firstName;
	
	@Column(nullable = false, length = 30)
	private String lastName;
	
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	@Column(nullable = false, length = 50)
	private String password;
	
	@Column(length = 30)
	private String phone;
	
	@Column(length = 50)
	private String safeQuestion;
	
	@Column(length = 20)
	private String safeAnswer;
	
}
