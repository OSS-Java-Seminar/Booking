package com.lolekibolek.Booking.persistence.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lolekibolek.Booking.persistence.api.exception.UserAlreadyExistException;
import com.lolekibolek.Booking.persistence.dtos.UserRegistrationDto;
import com.lolekibolek.Booking.persistence.entities.Role;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User save(UserRegistrationDto userDto) {
		User user = new User(userDto.getRole(), userDto.getGender(), userDto.getFirstName(), userDto.getLastName(),
				userDto.getEmail(), userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getPhone(), userDto.getSafeQuestion(),
				userDto.getSafeAnswer(), userDto.getAddress(), userDto.getCity(), userDto.getCountry(), Arrays.asList(new Role("ROLE_USER")));
		
		return userRepository.save(user);
	}

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
}
