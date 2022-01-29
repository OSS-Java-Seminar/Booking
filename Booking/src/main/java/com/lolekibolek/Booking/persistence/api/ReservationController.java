package com.lolekibolek.Booking.persistence.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ApartmentRepository;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;
import com.lolekibolek.Booking.persistence.services.ApartmentService;
import com.lolekibolek.Booking.persistence.services.ReservationService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;
	@Autowired
	ReservationController (ReservationService reservationServices) {
		this.reservationService = reservationServices;
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	
	@GetMapping()
    public String findAll(Model model) {
		User currentUser = userRepository.findByUsername(reservationService.getUser());
		model.addAttribute("user", currentUser);
		
		List<Reservation> reservations = reservationRepository.findAll();
		//List<Reservation> reservations = reservationRepository.findAllById(currentUser.getId());
		//Onda moras napravit boolean da vidis jel ta lista prazna ako nema nijednu
		
		model.addAttribute("reservations", reservations);
		
        return "allReservations";
    }
	
	@GetMapping("/sorted")
	public String sortAll(@RequestParam (defaultValue = "nameAsc") String sort,
			Model model) {
		User currentUser = userRepository.findByUsername(reservationService.getUser());
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
		
		return "allReservations";
	}
	
	@GetMapping("/{reservationId}")
    public String details(@PathVariable Integer reservationId, Model model) {
		System.out.println("lalalallaal");
		User currentUser = userRepository.findByUsername(reservationService.getUser());
		model.addAttribute("user", currentUser);
		User otherUser;
		Boolean isOwner = currentUser.getRole();
		Reservation reservation = reservationRepository.getById(reservationId);
		
		if (isOwner)
			otherUser = reservation.getUser();
		else
			otherUser = reservation.getApartment().getOwner();
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("otherUser", otherUser);
		model.addAttribute("reservation", reservation);
		model.addAttribute("isOwner", isOwner);
		
		System.out.println(reservationId);
		System.out.println(reservation);
		
        return "reservationDetails";
    }
	
	@GetMapping("/cancel")
    public String cancel(@RequestParam (value = "reservationId") Integer reservationId, Model model) {
		Reservation reservation = reservationRepository.getById(reservationId);
		reservation.setBooked(false);
		reservationRepository.save(reservation);
		
        return "redirect:/user";
    }
	
	@PostMapping()
    public String save(@RequestParam (value = "checkInDate") String checkInString, 
			@RequestParam (value = "checkOutDate") String checkOutString,
			@RequestParam (value = "apartmentId") int apartmentId, 
			@RequestParam (value = "totalPrice") float totalPrice,
			Model model) {
		
		User currentUser = userRepository.findByUsername(reservationService.getUser());
		model.addAttribute("user", currentUser);
		
		if (checkInString.isEmpty() || checkOutString.isEmpty()) {
			model.addAttribute("error", "An error occurrred. Please try again. ");
			return "badRequest";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date checkInDate = null;
		Date checkOutDate = null;
		try {
			checkInDate = sdf.parse(checkInString);
			checkOutDate = sdf.parse(checkOutString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Reservation reservation = new Reservation();
		reservation.setApartment(apartmentRepository.findById(apartmentId));
		reservation.setCheckInDate(checkInDate);
		reservation.setCheckOutDate(checkOutDate);
		reservation.setBooked(true);
		reservation.setTotalPrice(totalPrice);
		reservation.setUser(currentUser);
		
		if (reservationService.checkIfAvailable(apartmentRepository.findById(apartmentId), checkInDate, checkOutDate))
			reservationRepository.save(reservation);
		else {
			model.addAttribute("error", "Sorry, someone just booked your apartment. Please try again.");
			return "badRequest";
		}
		
		return "homeUser";
    }


}
