package com.lolekibolek.Booking.persistence.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.dtos.ReviewDto;
import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.entities.Review;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ApartmentRepository;
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
	private ApartmentRepository apartmentRepository;
	
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
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReservationId(id);

		model.addAttribute("reviewDto", reviewDto);

        return "reviewDetails";
    }
	
	@PostMapping("/save")
	public String saveReview(@ModelAttribute ReviewDto reviewDto,
    		Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		Review review = new Review();
		Double rating = 0.0;
		rating += reviewDto.getCleanessRating();
		rating += reviewDto.getComfortRating();
		rating += reviewDto.getLocationRating();
		rating += reviewDto.getHostRating();
		rating += reviewDto.getValueForMoneyRating();
		rating = rating / 5;
		
		review.setReservation(reservationRepository.findById(reviewDto.getReservationId()));
		review.setCleanessRating(reviewDto.getCleanessRating());
		review.setComfortRating(reviewDto.getComfortRating());
		review.setLocationRating(reviewDto.getLocationRating());
		review.setHostRating(reviewDto.getHostRating());
		review.setValueForMoneyRating(reviewDto.getValueForMoneyRating());
		review.setAverageRating(rating);
		review.setReview(reviewDto.getReview());
		
		reviewRepository.save(review);
		
		Reservation reservation = reservationRepository.findById(reviewDto.getReservationId());
		Apartment apartment = reservation.getApartment();
		
		Double previousRating = apartment.getRating();
		if (previousRating != 0.0) {
			Set<Reservation> temp = apartment.getReservations();
			List<Reservation> allReservations = new ArrayList<>(temp);
			int numberOfReviews = 0;
			if (allReservations.isEmpty() == false) {
				for (int i = 0; i < allReservations.size(); i++) {
					review = reviewRepository.findByReservation_id(allReservations.get(i).getId());
					if (review != null)
						numberOfReviews++;
				}
			}
			
			Double totalRating = (previousRating * numberOfReviews) + rating;
			totalRating = totalRating / (numberOfReviews + 1);
			
			apartment.setRating(totalRating);
		}
		
		else {
			apartment.setRating(rating);
		}
		
		apartmentRepository.save(apartment);
		
		model.addAttribute("message", "Your review has been saved.");
		return "success";
	}
}
