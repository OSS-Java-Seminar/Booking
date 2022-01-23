package com.lolekibolek.Booking.persistence.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolekibolek.Booking.persistence.entities.Review;
import com.lolekibolek.Booking.persistence.repositories.ReviewRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
    
    public Review findById(int id) {
    	return reviewRepository.findById(id);
    }
    
    public Review findByReservation_id(int id) {
    	return reviewRepository.findByReservation_id(id);
    }
    
    public List<Review> findAllByApartment_id(int id){ //dohvati sve recenzije jednog aparmtana
    	return null;
    }
}
