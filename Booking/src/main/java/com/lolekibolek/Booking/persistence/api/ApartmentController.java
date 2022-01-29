package com.lolekibolek.Booking.persistence.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ApartmentRepository;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;
import com.lolekibolek.Booking.persistence.services.ApartmentService;

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
	
	Tools tools = new Tools();
	
	@Autowired
	ApartmentController (ApartmentService apartmentServices) {
		this.apartmentService = apartmentServices;
	}
	
	@GetMapping
    public List<Apartment> findAll() {
        return apartmentService.findAll();
    }
	
	@GetMapping("/{id}")
    public Apartment findById(int id) {
        return apartmentService.findById(id);
    }
	
	@GetMapping("/details")
    public String book(@RequestParam (value = "checkInDate") String checkInString,
    		@RequestParam (value = "checkOutDate") String checkOutString, @RequestParam (value = "apartmentId") int apartmentId,
    		Model model) {
		User currentUser = userRepository.findByUsername(tools.getUser());
		model.addAttribute("user", currentUser);
		model.addAttribute("checkInDate", checkInString);
		model.addAttribute("checkOutDate", checkOutString);
		
		if (checkInString.isEmpty() || checkOutString.isEmpty()) {
			model.addAttribute("error", "An error occured. Please try again. ");
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
		
		long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
	    int numberOfNights = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
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
