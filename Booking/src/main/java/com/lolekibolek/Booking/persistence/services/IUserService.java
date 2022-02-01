package com.lolekibolek.Booking.persistence.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lolekibolek.Booking.persistence.api.exception.UserAlreadyExistException;
import com.lolekibolek.Booking.persistence.dtos.UserRegistrationDto;
import com.lolekibolek.Booking.persistence.entities.User;

public interface IUserService extends UserDetailsService{
	
	User save(UserRegistrationDto userRegistrationDto);
	
}
