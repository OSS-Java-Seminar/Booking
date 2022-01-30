package com.lolekibolek.Booking.persistence.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Review;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
import com.lolekibolek.Booking.persistence.repositories.ReviewRepository;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;
import com.lolekibolek.Booking.persistence.services.ReservationService;
import com.lolekibolek.Booking.persistence.services.ReviewService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ReviewController (ReviewService reviewServices) {
		this.reviewService = reviewServices;
	}
	
	@GetMapping
    public List<Review> findAll() {
        return reviewService.findAll();
    }
	
	@GetMapping("/{id}")
    public String findById(@PathVariable int id, Model model) {
		User currentUser = userRepository.findByUsername(reservationService.getUser());
		model.addAttribute("user", currentUser);
		
		model.addAttribute("reservation", reservationRepository.getById(id));
        return "reviewDetails";
    }
}
