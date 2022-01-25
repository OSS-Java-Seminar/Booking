package com.lolekibolek.Booking.persistence;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lolekibolek.Booking.persistence.entities.User;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class Home {
	
	//@PreAuthorize(roles = "OWNER")
	@GetMapping("/")
	public String home(Model model) {
		String username = getUser();
		model.addAttribute("username", username);
		return "index";
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
