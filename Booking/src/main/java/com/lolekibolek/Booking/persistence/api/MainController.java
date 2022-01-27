package com.lolekibolek.Booking.persistence.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ApartmentRepository;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;

	//@PreAuthorize(roles = "OWNER")
	@GetMapping()
	public String home(Model model) {
		User currentUser = userRepository.findByUsername(getUser());
		model.addAttribute("user", currentUser);
		return "home";
	}
	
	@DateTimeFormat(pattern="yyyy-mm-dd")
	@GetMapping("search")
	public String search(@RequestParam (value = "city") String city, @RequestParam (value = "checkInDate") String checkIn, @RequestParam (value = "checkOutDate") String checkOut, Model model) {
		User currentUser = userRepository.findByUsername(getUser());
		model.addAttribute("user", currentUser);
		List<Apartment> apartmentsInCity = apartmentRepository.findByCity(city);
		List<Apartment> apartments = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date checkInDate = null;
		try {
			checkInDate = sdf.parse(checkIn);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date checkOutDate = null;
		try {
			checkOutDate = sdf.parse(checkOut);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int j = 0; j < apartmentsInCity.size(); j++) {
			if (checkIfAvailable(apartmentsInCity.get(j), checkInDate, checkOutDate))
				apartments.add(apartmentsInCity.get(j));
		}
		
		if (apartments.isEmpty())
			return "notFound";
		model.addAttribute("apartments", apartments);
		return "apartments";
	}
	
	private boolean checkIfAvailable(Apartment apartment, Date s1, Date e1) {
		List<Reservation> allReservations = reservationRepository.findAll();
		Boolean check = true;
		
		for (int i = 0; i < allReservations.size(); i++) {
			if (allReservations.get(i).getApartment() == apartment) {
				Date s2 = allReservations.get(i).getCheckInDate();
				Date e2 = allReservations.get(i).getCheckOutDate();
				if(s1.before(s2) && e1.after(s2) ||
					       s1.before(e2) && e1.after(e2) ||
					       s1.before(s2) && e1.after(e2) ||
					       s1.after(s2) && e1.before(e2))
					check = false;
			}
		}
		return check;
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
