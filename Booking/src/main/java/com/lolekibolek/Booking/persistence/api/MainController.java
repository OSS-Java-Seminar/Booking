package com.lolekibolek.Booking.persistence.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.lolekibolek.Booking.persistence.repositories.ReviewRepository;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;
import com.lolekibolek.Booking.persistence.services.ReservationService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private ReservationService reservationService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping()
	public String home(Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		if (currentUser.getRole().equals(false))
			return "homeUser";
		
		List<Reservation> reservations = reservationRepository.findAll();
		List<Reservation> byId = new ArrayList<>();
		
		for (int i = 0; i < reservations.size(); i++) {
			if(currentUser.getId() == reservations.get(i).getApartment().getOwner().getId())
				byId.add(reservations.get(i));
		}
		
		Map<Reservation, String> todaysReservations = new HashMap<>();
		
		for (int i = 0; i < byId.size(); i++) {
			reservationService.checkIfToday(byId.get(i), todaysReservations);
		}
		model.addAttribute("reservations", todaysReservations);
		
		return "homeOwner";
	}
	
	@GetMapping("help")
	public String help(Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);

		return "help";
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@GetMapping("search")
	public String search(@RequestParam (value = "city") String city, 
			@RequestParam (value = "checkInDate") String checkInString, 
			@RequestParam (value = "checkOutDate") String checkOutString,
			@RequestParam (value = "maxPrice", required = false) String maxPrice,
			@RequestParam (value = "minBedroomNumber", required = false) String minBedroomNumber,
			@RequestParam (value = "minCapacity", required = false) String minCapacity,
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
			@RequestParam (value = "sortBy", required = false) String sortBy,
			Model model) {
		
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		if (city.isEmpty() || checkInString.isEmpty() || checkOutString.isEmpty()) {
			model.addAttribute("error", "Please enter city and dates. ");
			return "badRequest";
		}
		
		List<Apartment> apartmentsInCity = apartmentRepository.findByCity(city);
		List<Apartment> filteredApartments = filterApartments(apartmentsInCity, maxPrice, minBedroomNumber, minCapacity, petsAllowed, 
				smokingAllowed, disabledAccessible, balcony, kitchen,parking, seaView, pool, jacuzzi,
				iron, washingMachine, ac, heating, wifi);
		
		if (filteredApartments.isEmpty()){
			model.addAttribute("status", "Sorry, no apartments match your search.");
			return "notFound";
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate checkInDate = LocalDate.parse(checkInString, formatter);
		LocalDate checkOutDate = LocalDate.parse(checkOutString, formatter);
		
		if (checkOutDate.isBefore(checkInDate) || checkOutDate.equals(checkInDate)) {
			model.addAttribute("error", "Check-in date must be before check-out date. ");
			return "badRequest";
		}
		
		if (checkInDate.isBefore(LocalDate.now())) {
			model.addAttribute("error", "Can not make a reservation for past dates. ");
			return "badRequest";
		}
		
		List<Apartment> apartments = new ArrayList<>();
				
		for (int j = 0; j < filteredApartments.size(); j++) {
			if (reservationService.checkIfAvailable(filteredApartments.get(j), checkInDate, checkOutDate))
				apartments.add(filteredApartments.get(j));
		}
		
		if (apartments.isEmpty()){
			model.addAttribute("status", "Sorry, no apartments match your search.");
			return "notFound";
		}
		
		apartments = sortBy(apartments, sortBy);
		
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
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("maxPrice", maxPrice);
		model.addAttribute("minCapacity", minCapacity);
		model.addAttribute("minBedroomNumber", minBedroomNumber);
		return "searchApartments";
	}
	
	private List<Apartment> sortBy(List<Apartment> apartments, String sortBy) {
		if (sortBy != null) {
			if (sortBy.isEmpty() == false) {
				if (sortBy.equals("PriceMin")) {
					Collections.sort(apartments, (o1, o2) -> (o1.getPricePerNight().compareTo(o2.getPricePerNight())));
				}
				if (sortBy.equals("PriceMax")) {
					Collections.sort(apartments, (o1, o2) -> (o1.getPricePerNight().compareTo(o2.getPricePerNight())));
					Collections.reverse(apartments);
				}
				if (sortBy.equals("Rating")) {
					Collections.sort(apartments, (o1, o2) -> (o1.getRating().compareTo(o2.getRating())));
					Collections.reverse(apartments);
				}
			}
		}
		return apartments;
	}

	private List<Apartment> filterApartments(List<Apartment> apartmentsInCity, String maxPrice,
			String minBedroomNumber, String minCapacity, Boolean petsAllowed, Boolean smokingAllowed,
			Boolean disabledAccessible, Boolean balcony, Boolean kitchen, Boolean parking, Boolean seaView,
			Boolean pool, Boolean jacuzzi, Boolean iron, Boolean washingMachine, Boolean ac, Boolean heating,
			Boolean wifi) {
		List<Apartment> filteredApartments = new ArrayList<>();
		Boolean maxPriceBool, minBedroomNumberBool, minCapacityBool;
		
		maxPriceBool = maxPrice == null ? false : true;
		minBedroomNumberBool = minBedroomNumber == null ? false : true;
		minCapacityBool= minCapacity == null ? false : true;
		petsAllowed = petsAllowed == null ? false : true;
		smokingAllowed = smokingAllowed == null ? false : true;
		disabledAccessible = disabledAccessible == null ? false : true;
		balcony = balcony == null ? false : true;
		kitchen = kitchen == null ? false : true;
		parking = parking == null ? false : true;
		seaView = seaView == null ? false : true;
		pool = pool == null ? false : true;
		jacuzzi = jacuzzi == null ? false : true;
		iron = iron == null ? false : true;
		washingMachine = washingMachine == null ? false : true;
		ac = ac == null ? false : true;
		heating = heating == null ? false : true;
		wifi = wifi == null ? false : true;
				
		for (int i = 0; i < apartmentsInCity.size(); i++) {
			Boolean check = true;
			if (apartmentsInCity.get(i).getOpen() == false) check = false;
			if (petsAllowed == true && apartmentsInCity.get(i).isPetsAllowed() == false) check = false;
			if (smokingAllowed == true && apartmentsInCity.get(i).isSmokingAllowed() == false) check = false;
			if (disabledAccessible == true && apartmentsInCity.get(i).isDisabledAccessible() == false) check = false;
			if (balcony == true && apartmentsInCity.get(i).isBalcony() == false) check = false;
			if (kitchen == true && apartmentsInCity.get(i).isKitchen() == false) check = false;
			if (parking == true && apartmentsInCity.get(i).isParking() == false) check = false;
			if (seaView == true && apartmentsInCity.get(i).isSeaView() == false) check = false;
			if (pool == true && apartmentsInCity.get(i).isPool() == false) check = false;
			if (jacuzzi == true && apartmentsInCity.get(i).isJacuzzi() == false) check = false;
			if (iron == true && apartmentsInCity.get(i).isIron() == false) check = false;
			if (washingMachine == true && apartmentsInCity.get(i).isWashingMachine() == false) check = false;
			if (ac == true && apartmentsInCity.get(i).isAc() == false) check = false;
			if (heating == true && apartmentsInCity.get(i).isHeating() == false) check = false;
			if (wifi == true && apartmentsInCity.get(i).isWifi() == false) check = false;
			if (maxPriceBool == true) {
				if(maxPrice.isEmpty() == false) {
					int tmp = Integer.parseInt(maxPrice);
					if (apartmentsInCity.get(i).getPricePerNight() > tmp)
						check = false;
				}
			}
			if (minBedroomNumberBool == true) {
				if(minBedroomNumber.isEmpty() == false) {
					int tmp = Integer.parseInt(minBedroomNumber);
					if (apartmentsInCity.get(i).getBedroomNumber() < tmp)
						check = false;
				}
			}
			if (minCapacityBool == true) {
				if(minCapacity.isEmpty() == false) {
					int tmp = Integer.parseInt(minCapacity);
					if (apartmentsInCity.get(i).getCapacity() < tmp)
						check = false;
				}
			}
			
			if (check)
				filteredApartments.add(apartmentsInCity.get(i));
		}
		
		return filteredApartments;
	}
	
}
