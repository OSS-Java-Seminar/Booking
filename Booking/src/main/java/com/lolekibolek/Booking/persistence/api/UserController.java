package com.lolekibolek.Booking.persistence.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.entities.Review;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ApartmentRepository;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
import com.lolekibolek.Booking.persistence.repositories.ReviewRepository;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;
import com.lolekibolek.Booking.persistence.services.ReservationService;
import com.lolekibolek.Booking.persistence.services.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ReservationService reservationService;
	
	@GetMapping()
	public String profile(Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		if (currentUser.getRole().equals(false)) {
			List<Reservation> allReservations = new ArrayList<>(currentUser.getReservations());
			List<Reservation> futureReservations = new ArrayList<>();
			List<Reservation> pastReservations = new ArrayList<>();
			LocalDate today = LocalDate.now();
			
			for (int i = 0; i < allReservations.size(); i++) {
				if (allReservations.get(i).getCheckInDate().isAfter(today) || allReservations.get(i).getCheckInDate().equals(today)) {
					if (allReservations.get(i).getBooked().equals(true)) {
						System.out.println("Check-in: " + allReservations.get(i).getCheckInDate());
						System.out.println("Today " + LocalDate.now());
						futureReservations.add(allReservations.get(i));
					}
				}
			}
			
			for (int i = 0; i < allReservations.size(); i++) {
				if (allReservations.get(i).getCheckOutDate().isBefore(today) || allReservations.get(i).getCheckOutDate().equals(today)) {
					if (allReservations.get(i).getCheckOutDate().plusMonths(3).isAfter(today) && allReservations.get(i).getBooked().equals(true)) {
						Review review = reviewRepository.findByReservation_id(allReservations.get(i).getId());
						if (review == null)
							pastReservations.add(allReservations.get(i));
					}
				}
			}
						
			Boolean hasPast = true;
			Boolean hasFuture = true;
			if (futureReservations.isEmpty())
				hasFuture = false;
			if (pastReservations.isEmpty())
				hasPast = false;
			model.addAttribute("hasFuture", hasFuture);
			model.addAttribute("hasPast", hasPast);
			model.addAttribute("pastReservations", pastReservations);
			model.addAttribute("futureReservations", futureReservations);
			
			return "profileUser";
		}
		
		return "profileOwner";
	}
	
	@GetMapping("/edit") 
	public String edit(Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		return "editUser";
	}
	
	@PostMapping("/saveChanges")
	public String save(@Valid@ModelAttribute User user, BindingResult result,
			Model model) {
		model.addAttribute("user", user);
		
		if (result.hasErrors()) {
            return "editUser";
        }
		
		User saveUser;
		System.out.print(user.getId());
		if (userRepository.existsById(user.getId()))
			saveUser = userRepository.findById(user.getId());
		else
			saveUser = new User();
		System.out.print(saveUser.getId());
		
		saveUser.setId(user.getId());
		saveUser.setUsername(user.getUsername());
		saveUser.setGender(user.getGender());
		saveUser.setFirstName(user.getFirstName());
		saveUser.setLastName(user.getLastName());
		saveUser.setEmail(user.getEmail());
		saveUser.setPhone(user.getPhone());
		saveUser.setAddress(user.getAddress());
		saveUser.setCity(user.getCity());
		saveUser.setCountry(user.getCountry());
		
		userRepository.save(saveUser);
		
		model.addAttribute("message", "Your changes have been saved! ");
		return "success";
	}
	
	
	
	
}
