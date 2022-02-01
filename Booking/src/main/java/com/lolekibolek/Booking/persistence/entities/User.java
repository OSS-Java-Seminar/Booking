package com.lolekibolek.Booking.persistence.entities;

import java.util.Collection;
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
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
	private Set<Apartment> apartments;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Reservation> reservations;
	
	@Column(nullable = false)
	private Boolean role;
	
	@Column(nullable = false)
	private Boolean gender;
	
	@NotEmpty(message = "User's first name cannot be empty.")
	@Column(nullable = false, length = 30)
	private String firstName;
	
	@NotEmpty(message = "User's last name cannot be empty.")
	@Column(nullable = false, length = 30)
	private String lastName;
	
	@NotEmpty(message = "User's email cannot be empty.")
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	@NotEmpty(message = "Username cannot be empty.")
	@Column(nullable = false, length = 30, unique = true)
	private String username;
	
	@Column(nullable = false, length = 50)
	private String password;

	@Column(length = 30)
	private String phone;
	
	@Column(length = 50)
	private String safeQuestion;
	
	@Column(length = 20)
	private String safeAnswer;
	
	@NotEmpty(message = "Address cannot be empty.")
	@Column(nullable = false, length = 50)
	private String address;
	
	@NotEmpty(message = "City cannot be empty.")
	@Column(nullable = false, length = 50)
	private String city;
	
	@NotEmpty(message = "Country cannot be empty.")
	@Column(nullable = false, length = 50)
	private String country;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Apartment> getApartments() {
		return apartments;
	}

	public void setApartments(Set<Apartment> apartments) {
		this.apartments = apartments;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Boolean getRole() {
		return role;
	}

	public void setRole(Boolean role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSafeQuestion() {
		return safeQuestion;
	}

	public void setSafeQuestion(String safeQuestion) {
		this.safeQuestion = safeQuestion;
	}

	public String getSafeAnswer() {
		return safeAnswer;
	}

	public void setSafeAnswer(String safeAnswer) {
		this.safeAnswer = safeAnswer;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
}
