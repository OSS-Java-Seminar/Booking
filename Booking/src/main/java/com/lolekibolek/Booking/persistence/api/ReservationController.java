package com.lolekibolek.Booking.persistence.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;
import com.lolekibolek.Booking.persistence.services.ApartmentService;
import com.lolekibolek.Booking.persistence.services.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	
	@GetMapping()
    public String findAll(Model model) {
		List<Reservation> reservations = reservationRepository.findAll();
		
		User currentUser = userRepository.findByUsername(getUser());
		model.addAttribute("user", currentUser);
		
		model.addAttribute("reservations", reservations);
		
		if (currentUser.getRole().equals(false))
			return "reservationsUser";
        return "reservationsOwner";
    }
	
	@GetMapping("/{id}")
    public String findById(@PathVariable int id, Model model ) {
		Reservation reservation = reservationRepository.findById(id);
		model.addAttribute("reservation", reservation);
        return "reservation";
	}
    
	
	public String getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    return currentUserName;
		}
		return "Guest";
	}
	
}
