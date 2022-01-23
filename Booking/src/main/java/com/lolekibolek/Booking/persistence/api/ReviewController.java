package com.lolekibolek.Booking.persistence.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Review;
import com.lolekibolek.Booking.persistence.services.ReviewService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	
	@Autowired
	ReviewController (ReviewService reviewServices) {
		this.reviewService = reviewServices;
	}
	
	@GetMapping
    public List<Review> findAll() {
        return reviewService.findAll();
    }
	
	@GetMapping("/{id}")
    public Review findById(int id) {
        return reviewService.findById(id);
    }
}
