package com.lolekibolek.Booking.persistence.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.entities.Review;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ApartmentRepository;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
import com.lolekibolek.Booking.persistence.repositories.ReviewRepository;
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
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ReservationService reservationService;
	
	@GetMapping()
	public String profile(Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		if (currentUser.getRole().equals(false)) {
			List<Reservation> allReservations = new ArrayList<>(currentUser.getReservations());
			List<Reservation> futureReservations = new ArrayList<>();
			List<Reservation> pastReservations = new ArrayList<>();
			LocalDate today = LocalDate.now();
			
			for (int i = 0; i < allReservations.size(); i++) {
				if (allReservations.get(i).getCheckInDate().isAfter(today) || allReservations.get(i).getCheckInDate().equals(today)) {
					if (allReservations.get(i).getBooked().equals(true)) {
						futureReservations.add(allReservations.get(i));
					}
				}
			}
			
			for (int i = 0; i < allReservations.size(); i++) {
				if (allReservations.get(i).getCheckOutDate().isBefore(today) || allReservations.get(i).getCheckOutDate().equals(today)) {
					if (allReservations.get(i).getCheckOutDate().plusMonths(3).isAfter(today) && allReservations.get(i).getBooked().equals(true)) {
						Review review = reviewRepository.findByReservation_id(allReservations.get(i).getId());
						if (review == null)
							pastReservations.add(allReservations.get(i));
					}
				}
			}
						
			Boolean hasPast = true;
			Boolean hasFuture = true;
			if (futureReservations.isEmpty())
				hasFuture = false;
			if (pastReservations.isEmpty())
				hasPast = false;
			model.addAttribute("hasFuture", hasFuture);
			model.addAttribute("hasPast", hasPast);
			model.addAttribute("pastReservations", pastReservations);
			model.addAttribute("futureReservations", futureReservations);
			
			return "profileUser";
		}
		
		List<Reservation> allReservations = reservationRepository.findAll();
		List<Reservation> ownerReservations = new ArrayList<>();
		for (int i = 0; i < allReservations.size(); i++) {
			if(currentUser.getId() == allReservations.get(i).getApartment().getOwner().getId())
				ownerReservations.add(allReservations.get(i));
		}
		
		LocalDate todayDate = LocalDate.now();
		
		Double reviewCard = 0.0;
		int reservationCard = 0;
		Double monthlyCard = 0.0;
		Double yearlyCard = 0.0;
		Double reviewPreviousMonth = 0.0;
		int reservationPreviousMonth = 0;
		Double previousMonthly = 0.0;
		Double previousYearly = 0.0;
		LocalDate monthStart = LocalDate.of(todayDate.getYear(), todayDate.getMonth(), 01);
		LocalDate previousMonth;
		
		if (monthStart.getMonthValue() - 1 == 0)
			previousMonth = LocalDate.of(todayDate.getYear()-1, 12, 01);
		else
			previousMonth = LocalDate.of(todayDate.getYear(), todayDate.getMonthValue() - 1, 01);
		
		for (int i = 0; i < ownerReservations.size(); i++) {
			if (ownerReservations.get(i).getCheckOutDate().isAfter(monthStart) && ownerReservations.get(i).getCheckOutDate().isBefore(todayDate)
					&& ownerReservations.get(i).ifBooked().equals(true)) {
				monthlyCard+= ownerReservations.get(i).getTotalPrice();
				reviewCard+= ownerReservations.get(i).getApartment().getRating();
				reservationCard++;
			}
		}
		reviewCard = ReservationService.round(reviewCard/reservationCard, 2);
		for (int i = 0; i < ownerReservations.size(); i++) {
			if (ownerReservations.get(i).getCheckOutDate().isAfter(previousMonth) && ownerReservations.get(i).getCheckOutDate().isBefore(monthStart)
					&& ownerReservations.get(i).ifBooked().equals(true)) {
				previousMonthly+= ownerReservations.get(i).getTotalPrice();
				reviewPreviousMonth+= ownerReservations.get(i).getApartment().getRating();
				reservationPreviousMonth++;
			}
		}
		reviewPreviousMonth = reviewPreviousMonth / reservationPreviousMonth;
		Double monthlyIncrease = (monthlyCard - previousMonthly) / previousMonthly * 100;
			monthlyIncrease = ReservationService.round(monthlyIncrease, 2);
		model.addAttribute("redMonthlyCard", redCard(monthlyCard, previousMonthly));
		
		int reservationIncrease = reservationCard - reservationPreviousMonth;
		model.addAttribute("redReservationCard", redCard(Double.valueOf(reservationCard), (Double.valueOf(reservationPreviousMonth))));		
		
		Double reviewIncrease = (reviewCard - reviewPreviousMonth) / reviewPreviousMonth * 100;
			reviewIncrease = ReservationService.round(reviewIncrease, 2);
		model.addAttribute("redReviewCard", redCard(reviewCard, reviewPreviousMonth));

		LocalDate yearStart = LocalDate.of(todayDate.getYear(), 01, 01);
		LocalDate previousYear = LocalDate.of(todayDate.getYear() - 1, 01, 01);
		
		for (int i = 0; i < ownerReservations.size(); i++) {
			if (ownerReservations.get(i).getCheckOutDate().isAfter(yearStart) && ownerReservations.get(i).getCheckOutDate().isBefore(todayDate)
					&& ownerReservations.get(i).ifBooked().equals(true))
				yearlyCard+= ownerReservations.get(i).getTotalPrice();
		}
		for (int i = 0; i < ownerReservations.size(); i++) {
			if (ownerReservations.get(i).getCheckOutDate().isAfter(previousYear) && ownerReservations.get(i).getCheckOutDate().isBefore(yearStart)
					&& ownerReservations.get(i).ifBooked().equals(true))
				previousYearly+= ownerReservations.get(i).getTotalPrice();
		}
		Double yearlyIncrease = (yearlyCard - previousYearly) / previousYearly * 100;
			yearlyIncrease = ReservationService.round(yearlyIncrease, 2);
		model.addAttribute("redYearlyCard", redCard(yearlyCard, previousYearly));
		
		
		model.addAttribute("reservationCard", reservationCard);
		model.addAttribute("reservationIncrease", reservationIncrease);
		model.addAttribute("reviewCard", reviewCard);
		model.addAttribute("reviewIncrease", reviewIncrease);
		model.addAttribute("monthlyCard", monthlyCard);
		model.addAttribute("monthlyIncrease", monthlyIncrease);
		model.addAttribute("yearlyCard", yearlyCard);
		model.addAttribute("yearlyIncrease", yearlyIncrease);
		
		return "profileOwner";
	}
	
	public String redCard(Double month, Double previousMonth) {
		if (month < previousMonth)
		return "red";
		else if (previousMonth == 0.0)
			return "no";
		else if (month > previousMonth)
			return "green";
		//else if (month == previousMonth)
		return "same";
	}
	
	@GetMapping("/edit") 
	public String edit(Model model) {
		User currentUser = reservationService.getUser();
		model.addAttribute("user", currentUser);
		
		return "editUser";
	}
	
	@PostMapping("/saveChanges")
	public String save(@Valid@ModelAttribute User user, BindingResult result,
			Model model) {
		model.addAttribute("user", user);
		
		System.out.println(result);
		
		if (result.hasErrors()) {
			return "redirect:/user/edit?error";
        }
		
		List<User> allUsers = userRepository.findAll();
		Boolean uniqueUsername = true;
		Boolean uniqueEmail = true;
		if (allUsers.isEmpty() == false) {
			for (int i = 0; i < allUsers.size(); i++) {
				if(allUsers.get(i).getId() != user.getId()) {
					System.out.println("uslo");
					if (allUsers.get(i).getEmail().equals(user.getEmail()))
						uniqueEmail = false;
					if (allUsers.get(i).getUsername().equals(user.getUsername()))
						uniqueUsername = false;
				}
			}
		}
		
		if (uniqueUsername.equals(false) && uniqueEmail.equals(true))
			return "redirect:/user/edit?usernameError";
		if (uniqueUsername.equals(true) && uniqueEmail.equals(false))
			return "redirect:/user/edit?emailError";
		if (uniqueUsername.equals(false) && uniqueEmail.equals(false))
			return "redirect:/user/edit?usernameError&emailError";
		
		User saveUser;
		System.out.print(user.getId());
		if (userRepository.existsById(user.getId()))
			saveUser = userRepository.findById(user.getId());
		else
			saveUser = new User();
		System.out.print(saveUser.getId());
		
		saveUser.setId(user.getId());
		saveUser.setUsername(user.getUsername());
		saveUser.setGender(user.getGender());
		saveUser.setFirstName(user.getFirstName());
		saveUser.setLastName(user.getLastName());
		saveUser.setEmail(user.getEmail());
		saveUser.setPhone(user.getPhone());
		saveUser.setAddress(user.getAddress());
		saveUser.setCity(user.getCity());
		saveUser.setCountry(user.getCountry());
		
		userRepository.save(saveUser);
		
		model.addAttribute("message", "Your changes have been saved! ");
		return "success";
	}
	
	
}
