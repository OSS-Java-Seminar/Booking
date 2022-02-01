package com.lolekibolek.Booking.persistence.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lolekibolek.Booking.persistence.dtos.UserRegistrationDto;
import com.lolekibolek.Booking.persistence.services.IUserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
	
	private IUserService userService;

	public UserRegistrationController(IUserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}
	
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userDto) {
		userService.save(userDto);
		return "redirect:/registration?success";
	}
}
