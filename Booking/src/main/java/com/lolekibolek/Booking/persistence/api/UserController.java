package com.lolekibolek.Booking.persistence.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ApartmentRepository;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
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
	private ReservationService reservationService;
	
	@GetMapping()
	public String profile(Model model) {
		User currentUser = userRepository.findByUsername(reservationService.getUser());
		model.addAttribute("user", currentUser);
		
		if (currentUser.getRole().equals(false)) {
			List<Reservation> allReservations = new ArrayList<>(currentUser.getReservations());
			List<Reservation> futureReservations = new ArrayList<>();
			List<Reservation> pastReservations = new ArrayList<>();
			LocalDate today = LocalDate.now();
			
			for (int i = 0; i < allReservations.size(); i++) {
				if (allReservations.get(i).getCheckInDate().isAfter(today) || allReservations.get(i).getCheckInDate().equals(today))
					futureReservations.add(allReservations.get(i));
			}
			
			for (int i = 0; i < allReservations.size(); i++) {
				if (allReservations.get(i).getCheckOutDate().isBefore(today) || allReservations.get(i).getCheckOutDate().equals(today))
					pastReservations.add(allReservations.get(i));
			}
			
			Boolean hasPast = true;
			Boolean hasFuture = true;
			if (futureReservations.isEmpty())
				hasFuture = false;
			if (pastReservations.isEmpty())
				hasPast = false;
			model.addAttribute("hasFuture", hasFuture);
			model.addAttribute("hasPast", hasPast);
			model.addAttribute(pastReservations);
			model.addAttribute(futureReservations);
			
			return "profileUser";
		}
		
		return "profileOwner";
	}
	
}
