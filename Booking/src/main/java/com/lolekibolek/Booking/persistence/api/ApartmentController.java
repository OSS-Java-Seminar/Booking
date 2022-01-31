package com.lolekibolek.Booking.persistence.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.dtos.ApartmentDto;
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
	private ReservationService reservationService;
	
	@Autowired
	ApartmentController (ApartmentService apartmentServices) {
		this.apartmentService = apartmentServices;
	}
	
	@GetMapping
    public String findAll(Model model) {
		User currentUser = userRepository.findByUsername(reservationService.getUser());
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
		User currentUser = userRepository.findByUsername(reservationService.getUser());
		model.addAttribute("user", currentUser);
		
		Apartment apartmentDto =  new Apartment();
		model.addAttribute("apartmentDto", apartmentDto);
		
		return "newApartment";
	}
	
	@GetMapping("/update") 
	public String update(@RequestParam int id,
			Model model) {
		User currentUser = userRepository.findByUsername(reservationService.getUser());
		model.addAttribute("user", currentUser);
		
		Apartment apartment = apartmentRepository.findById(id);
		apartment.setId(id);
		model.addAttribute("apartmentDto", apartment);
		
		return "newApartment";
	}
	
	@PostMapping("/save")
	public String saveApartment(@ModelAttribute Apartment apartmentDto,
			Model model) {
		User currentUser = userRepository.findByUsername(reservationService.getUser());
		model.addAttribute("user", currentUser);
		
		apartmentDto.setOwner(currentUser);
		apartmentRepository.save(apartmentDto);
		
		model.addAttribute("message", "Your apartment has been saved.");
		return "success";
	}
	
	@GetMapping("/details")
    public String book(@RequestParam (value = "checkInDate") String checkInString,
    		@RequestParam (value = "checkOutDate") String checkOutString, @RequestParam (value = "apartmentId") int apartmentId,
    		Model model) {
		User currentUser = userRepository.findByUsername(reservationService.getUser());
		model.addAttribute("user", currentUser);
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
		
		Apartment apartment = apartmentRepository.getById(apartmentId);
		model.addAttribute("apartment", apartment);
		
		float totalPrice = numberOfNights * apartment.getPricePerNight();
		model.addAttribute("totalPrice", totalPrice);
		
		return "apartmentDetails";
    }

	public List<Apartment> findByCity(String city) {
		// TODO Auto-generated method stub
		return apartmentService.findByCity(city);
	}
}
