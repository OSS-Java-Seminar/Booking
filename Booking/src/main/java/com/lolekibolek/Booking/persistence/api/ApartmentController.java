package com.lolekibolek.Booking.persistence.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.entities.Review;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ApartmentRepository;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
import com.lolekibolek.Booking.persistence.repositories.ReviewRepository;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;
import com.lolekibolek.Booking.persistence.services.ApartmentService;
import com.lolekibolek.Booking.persistence.services.ReservationService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/apartments")
@AllArgsConstructor
public class ApartmentController {

	private final ApartmentService apartmentService;
	
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
	
	@Autowired
	ApartmentController (ApartmentService apartmentServices) {
		this.apartmentService = apartmentServices;
	}
	
	@GetMapping
    public String findAll(Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		List<Apartment> apartments= apartmentRepository.findAll();
		List<Apartment> byId = new ArrayList<>();
		
		for (int i = 0; i < apartments.size(); i++) {
			if(currentUser.getId() == apartments.get(i).getOwner().getId())
				byId.add(apartments.get(i));
		}
		
		if (byId.isEmpty()) {
			model.addAttribute("status", "Please add an apartment first.");
			//?????'dodatttttttttt
		}
		
		model.addAttribute("apartments", byId);
		
        return "allApartments";
    }
	
	@GetMapping("/{id}")
    public Apartment findById(int id) {
        return apartmentService.findById(id);
    }
	
	@GetMapping("/add") 
	public String addNew(Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		Apartment apartmentDto =  new Apartment();
		model.addAttribute("apartmentDto", apartmentDto);
		
		return "newApartment";
	}
	
	@GetMapping("/update") 
	public String update(@RequestParam int id,
			Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		Apartment apartment = apartmentRepository.findById(id);
		apartment.setId(id);
		model.addAttribute("apartment", apartment);
		
		return "newApartment";
	}
	
	@PostMapping("/save")
	public String saveApartment(@Valid@ModelAttribute("apartment") Apartment apartment, BindingResult result,
			Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		//model.addAttribute("apartment", apartment);
		
		if (result.hasErrors()) {
            return "newApartment";
        }
		
		Apartment saveApartment;
		
		if (apartmentRepository.existsById(apartment.getId()))
			saveApartment = apartmentRepository.findById(apartment.getId());
		else
			saveApartment = new Apartment();
		
		saveApartment.setOwner(currentUser);
		saveApartment.setName(apartment.getName());
		saveApartment.setCountry(apartment.getCountry());
		saveApartment.setCity(apartment.getCity());
		saveApartment.setAddress(apartment.getAddress());
		saveApartment.setPricePerNight(apartment.getPricePerNight());
		saveApartment.setCapacity(apartment.getCapacity());
		saveApartment.setSize(apartment.getSize());
		saveApartment.setBedroomNumber(apartment.getBedroomNumber());
		saveApartment.setDescription(apartment.getDescription());
		saveApartment.setPicture(apartment.getPicture());
		saveApartment.setPetsAllowed(apartment.isPetsAllowed());
		saveApartment.setSmokingAllowed(apartment.isSmokingAllowed());
		saveApartment.setDisabledAccessible(apartment.isDisabledAccessible());
		saveApartment.setBalcony(apartment.isBalcony());
		saveApartment.setKitchen(apartment.isKitchen());
		saveApartment.setParking(apartment.isParking());
		saveApartment.setSeaView(apartment.isSeaView());
		saveApartment.setPool(apartment.isSeaView());
		saveApartment.setJacuzzi(apartment.isJacuzzi());
		saveApartment.setIron(apartment.isIron());
		saveApartment.setWashingMachine(apartment.isWashingMachine());
		saveApartment.setAc(apartment.isAc());
		saveApartment.setHeating(apartment.isHeating());
		saveApartment.setWifi(apartment.isWifi());
		
		apartmentRepository.save(saveApartment);
		
		model.addAttribute("message", "Your apartment has been saved.");
		return "success";
	}
	
	@GetMapping("/details")
    public String book(@RequestParam (value = "checkInDate", required = false) String checkInString,
    		@RequestParam (value = "checkOutDate", required = false) String checkOutString, 
    		@RequestParam (value = "apartmentId") int apartmentId,
    		Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		Apartment apartment = apartmentRepository.getById(apartmentId);
		model.addAttribute("apartment", apartment);
		
		Set<Reservation> temp = apartment.getReservations();
		List<Reservation> reservations = new ArrayList<>(temp);
		List<Review> reviews = new ArrayList<>();
		if (reservations.isEmpty() == false) {
			for (int i = 0; i < reservations.size(); i++) {
				Review review = reviewRepository.findByReservation_id(reservations.get(i).getId());
				if (review != null)
					reviews.add(review);
			}
		}
		
		Boolean isReviews;
		if (reviews.isEmpty())
			isReviews = false;
		else
			isReviews = true;
		model.addAttribute("reviews", reviews);
		model.addAttribute("isReviews", isReviews);
		
		if (currentUser.getRole()) {
			return "apartmentDetails";
		}
		
		model.addAttribute("checkInDate", checkInString);
		model.addAttribute("checkOutDate", checkOutString);
		
		if (checkInString.isEmpty() || checkOutString.isEmpty()) {
			model.addAttribute("error", "An error occured. Please try again. ");
			return "badRequest";
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate checkInDate = LocalDate.parse(checkInString, formatter);
		LocalDate checkOutDate = LocalDate.parse(checkOutString, formatter);
		
	    long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
	    model.addAttribute("numberOfNights", numberOfNights);
		
		float totalPrice = numberOfNights * apartment.getPricePerNight();
		model.addAttribute("totalPrice", totalPrice);
		
		return "apartmentDetails";
    }

	public List<Apartment> findByCity(String city) {
		// TODO Auto-generated method stub
		return apartmentService.findByCity(city);
	}
}
