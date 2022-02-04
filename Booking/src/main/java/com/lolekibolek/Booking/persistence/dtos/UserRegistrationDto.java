package com.lolekibolek.Booking.persistence.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRegistrationDto {
    @NotEmpty
    private String firstName;
    
    @NotEmpty
    private String lastName;
    
    @NotEmpty
    private String password;
    
    @NotEmpty
    @Email
    private String email;
    
    @NotNull
    private Boolean role;
    
    @NotNull
    private Boolean gender;
    
    @NotEmpty
    private String username;
    
    @NotEmpty
    private String phone;

	@NotEmpty
    private String address;
    
    @NotEmpty
    private String city;
    
    @NotEmpty
    private String country;
    
    public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public UserRegistrationDto(@NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String password,
			@NotEmpty String email, @NotNull Boolean role, @NotNull Boolean gender, @NotEmpty String username,
			@NotEmpty String phone,	@NotEmpty String address, @NotEmpty String city, @NotEmpty String country) {
		
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.gender = gender;
		this.username = username;
		this.phone = phone;
		this.address = address;
		this.city = city;
		this.country = country;
	}

	public UserRegistrationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Boolean getRole() {
		// TODO Auto-generated method stub
		return role;
	}
    
    
}
