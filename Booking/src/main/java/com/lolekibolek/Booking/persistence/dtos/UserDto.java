package com.lolekibolek.Booking.persistence.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDto {
    @NotNull
    @NotEmpty
    private String firstName;
    
    @NotNull
    @NotEmpty
    private String lastName;
    
    @NotNull
    @NotEmpty
    private String password;
//  private String matchingPassword;
    
    @NotNull
    @NotEmpty
    private String email;
    
    @NotEmpty
    private Boolean role;

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

	public UserDto(@NotNull @NotEmpty String firstName, @NotNull @NotEmpty String lastName,
			@NotNull @NotEmpty String password, @NotNull @NotEmpty String email, @NotEmpty Boolean role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Boolean getRole() {
		// TODO Auto-generated method stub
		return role;
	}
    
    
}
