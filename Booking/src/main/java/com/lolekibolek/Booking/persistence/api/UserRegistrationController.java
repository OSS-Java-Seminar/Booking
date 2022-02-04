package com.lolekibolek.Booking.persistence.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lolekibolek.Booking.persistence.dtos.UserRegistrationDto;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;
import com.lolekibolek.Booking.persistence.services.IUserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
	
	private IUserService userService;
	
	@Autowired
	UserRepository userRepository;

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
	public String registerUserAccount(@Valid@ModelAttribute("user") UserRegistrationDto userDto, BindingResult result) {
		
		if (result.hasErrors()) {
			System.out.println("uslo");
			
            return "redirect:/registration?error";
        }
		
		List<User> allUsers = userRepository.findAll();
		Boolean uniqueEmail = true;
		Boolean uniqueUsername = true;
		if (allUsers.isEmpty() == false) {
			for (int i = 0; i < allUsers.size(); i++) {
				if (allUsers.get(i).getEmail().equals(userDto.getEmail()))
					uniqueEmail = false;
				if (allUsers.get(i).getUsername().equals(userDto.getUsername()))
					uniqueUsername = false;
			}
		}
		
		if (uniqueUsername.equals(false) && uniqueEmail.equals(true))
			return "redirect:/registration?usernameError";
		if (uniqueUsername.equals(true) && uniqueEmail.equals(false))
			return "redirect:/registration?emailError";
		if (uniqueUsername.equals(false) && uniqueEmail.equals(false))
			return "redirect:/registration?usernameError&emailError";
		
		userService.save(userDto);
		return "redirect:/registration?success";
	}
}
