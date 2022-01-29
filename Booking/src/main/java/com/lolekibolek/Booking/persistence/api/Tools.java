package com.lolekibolek.Booking.persistence.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;

public class Tools {
	
	@Autowired
	private ReservationRepository reservationRepository;

	public String getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    return currentUserName;
		}
		return "Guest";
	}
	
	public boolean checkIfAvailable(Apartment apartment, Date s1, Date e1) {
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
	
	@SuppressWarnings("deprecation")
	public Date getToday() {
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		
		return today;
	}

	public Tools() {
		super();
		// TODO Auto-generated constructor stub
	}

}
