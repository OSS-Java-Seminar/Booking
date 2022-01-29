package com.lolekibolek.Booking.persistence.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
		if (currentUser.getRole().equals(false))
			return "homeUser";
		
		List<Reservation> reservations = reservationRepository.findAll();
		
		Map<Reservation, String> todaysReservations = new HashMap<>();
		
		for (int i = 0; i < reservations.size(); i++) {
			checkIfToday(reservations.get(i), todaysReservations);
		}
		model.addAttribute("reservations", todaysReservations);
		
		return "homeOwner";
	}
	
	private boolean checkIfToday(Reservation reservation, Map<Reservation, String> todaysReservations) {
		Boolean check = true;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		Date todayDate = null;
		try {
			todayDate = dateFormatter.parse(dateFormatter.format(new Date() ));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date checkInDate = reservation.getCheckInDate();
		Date checkOutDate = reservation.getCheckOutDate();
		
		if (checkInDate.equals(todayDate) && reservation.ifBooked().equals(true))
			todaysReservations.put(reservation, "Arrival");
		else if (checkInDate.before(todayDate) && checkOutDate.after(new Date()))
			todaysReservations.put(reservation, "Stayover");
		else if (checkInDate.equals(todayDate) && reservation.ifBooked().equals(false))
			todaysReservations.put(reservation, "Cancelled");
		else if (checkOutDate.equals(todayDate))
			todaysReservations.put(reservation, "Due Out");
		else
			check = false;
	
		return check;
	}

	@GetMapping("help")
	public String help(Model model) {
		User currentUser = userRepository.findByUsername(getUser());
		model.addAttribute("user", currentUser);
		
		if (currentUser.getRole().equals(false))
			return "helpUser";
		return "helpOwner";
	}
	
	@GetMapping("profile")
	public String profile(Model model) {
		User currentUser = userRepository.findByUsername(getUser());
		model.addAttribute("user", currentUser);
		
		if (currentUser.getRole().equals(false))
			return "profileUser";
		return "profileOwner";
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@GetMapping("search")
	public String search(@RequestParam (value = "city") String city, 
			@RequestParam (value = "checkInDate") String checkInString, 
			@RequestParam (value = "checkOutDate") String checkOutString,
			@RequestParam (value = "maxPrice", required = false) Integer maxPrice,
			@RequestParam (value = "minBedroomNumber", required = false) Integer minBedroomNumber,
			@RequestParam (value = "minCapacity", required = false) Integer minCapacity,
			@RequestParam (value = "petsAllowed", required = false) Boolean petsAllowed,
			@RequestParam (value = "smokingAllowed", required = false) Boolean smokingAllowed,
			@RequestParam (value = "disabledAccessible", required = false) Boolean disabledAccessible,
			@RequestParam (value = "balcony", required = false) Boolean balcony,
			@RequestParam (value = "kitchen", required = false) Boolean kitchen,
			@RequestParam (value = "parking", required = false) Boolean parking,
			@RequestParam (value = "seaView", required = false) Boolean seaView,
			@RequestParam (value = "pool", required = false) Boolean pool,
			@RequestParam (value = "jacuzzi", required = false) Boolean jacuzzi,
			@RequestParam (value = "iron", required = false) Boolean iron,
			@RequestParam (value = "washingMachine", required = false) Boolean washingMachine,
			@RequestParam (value = "ac", required = false) Boolean ac,
			@RequestParam (value = "heating", required = false) Boolean heating,
			@RequestParam (value = "wifi", required = false) Boolean wifi,
			Model model) {
		
		User currentUser = userRepository.findByUsername(getUser());
		model.addAttribute("user", currentUser);
		
		if (city.isEmpty() || checkInString.isEmpty() || checkOutString.isEmpty()) {
			model.addAttribute("error", "Please enter city and dates. ");
			return "badRequest";
		}
		
		List<Apartment> apartmentsInCity = apartmentRepository.findByCity(city);
		List<Apartment> apartments = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date checkInDate = null;
		Date checkOutDate = null;
		try {
			checkInDate = sdf.parse(checkInString);
			checkOutDate = sdf.parse(checkOutString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (checkOutDate.compareTo(checkInDate) < 1) {
			model.addAttribute("error", "Check-in date must be before check-out date. ");
			return "badRequest";
		}
		
		if (checkInDate.before(new Date())) {
			model.addAttribute("error", "Can not make a reservation for past dates. ");
			return "badRequest";
		}
				
		for (int j = 0; j < apartmentsInCity.size(); j++) {
			if (checkIfAvailable(apartmentsInCity.get(j), checkInDate, checkOutDate))
				apartments.add(apartmentsInCity.get(j));
		}
		
		if (apartments.isEmpty())
			return "notFound";
		model.addAttribute("apartments", apartments);
		model.addAttribute("checkInDate", checkInString);
		model.addAttribute("checkOutDate", checkOutString);
		model.addAttribute("city", city);
		model.addAttribute("petsAllowed", petsAllowed);
		model.addAttribute("smokingAllowed", smokingAllowed);
		model.addAttribute("disabledAccessible", disabledAccessible);
		model.addAttribute("balcony", balcony);
		model.addAttribute("kitchen", kitchen);
		model.addAttribute("parking", parking);
		model.addAttribute("seaView", seaView);
		model.addAttribute("pool", pool);
		model.addAttribute("jacuzzi", jacuzzi);
		model.addAttribute("iron", iron);
		model.addAttribute("washingMachine", washingMachine);
		model.addAttribute("ac", ac);
		model.addAttribute("heating", heating);
		model.addAttribute("wifi", wifi);
		return "searchApartments";
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
