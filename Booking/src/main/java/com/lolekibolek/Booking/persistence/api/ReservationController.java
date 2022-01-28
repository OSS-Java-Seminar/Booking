package com.lolekibolek.Booking.persistence.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "allReservationsOwner";
    }
	
	@GetMapping("/sorted")
	public String sortAll(@RequestParam (defaultValue = "nameAsc") String sort,
			Model model) {
		User currentUser = userRepository.findByUsername(getUser());
		model.addAttribute("user", currentUser);
		
		List<Reservation> sortedReservations = reservationRepository.findAll();
		
		if(sort.equals("nameAsc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.ASC, "apartment"));
		if(sort.equals("nameDesc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.DESC, "apartment"));
		if(sort.equals("checkInAsc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.ASC, "checkInDate"));
		if(sort.equals("checkInDesc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.DESC, "checkInDate"));
		if(sort.equals("checkOutAsc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.ASC, "checkOutDate"));
		if(sort.equals("checkOutDesc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.DESC, "checkOutDate"));
		if(sort.equals("bookedAsc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.ASC, "booked"));
		if(sort.equals("bookedDesc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.DESC, "booked"));
		if(sort.equals("totalPriceAsc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.ASC, "totalPrice"));
		if(sort.equals("totalPriceDesc")) 
			sortedReservations = reservationRepository.findAll(Sort.by(Sort.Direction.DESC, "totalPrice"));
		
		model.addAttribute("reservations", sortedReservations);
		
		return "allReservationsOwner";
	}
	
	@GetMapping("/{id}")
    public String findById(@PathVariable int id, Model model ) {
		User currentUser = userRepository.findByUsername(getUser());
		model.addAttribute("user", currentUser);
		
		Reservation reservation = reservationRepository.findById(id);
		model.addAttribute("reservation", reservation);

		
        return "reservationOwner";
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
