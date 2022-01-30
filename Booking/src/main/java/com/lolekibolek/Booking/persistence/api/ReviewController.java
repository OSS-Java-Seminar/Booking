package com.lolekibolek.Booking.persistence.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Review;
import com.lolekibolek.Booking.persistence.repositories.ReviewRepository;
import com.lolekibolek.Booking.persistence.services.ReviewService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	
	@Autowired
	private ReviewRepository reviewRepository;

	
	@Autowired
	ReviewController (ReviewService reviewServices) {
		this.reviewService = reviewServices;
	}
	
	@GetMapping
    public List<Review> findAll() {
        return reviewService.findAll();
    }
	
	@GetMapping("/{id}")
    public String findById(@PathVariable int id) {
		
		
        return "reviewDetails";
    }
}
