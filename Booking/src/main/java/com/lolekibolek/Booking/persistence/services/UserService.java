package com.lolekibolek.Booking.persistence.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User findById(int id) {
    	return userRepository.findById(id);
    }
    
    public User save(User newUser) {
    	return userRepository.save(newUser);
    }
	
}
