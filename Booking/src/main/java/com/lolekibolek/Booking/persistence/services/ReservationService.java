package com.lolekibolek.Booking.persistence.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lolekibolek.Booking.persistence.entities.Apartment;
import com.lolekibolek.Booking.persistence.entities.Reservation;
import com.lolekibolek.Booking.persistence.entities.User;
import com.lolekibolek.Booking.persistence.repositories.ReservationRepository;
import com.lolekibolek.Booking.persistence.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
    
    public Reservation findById(int id) {
    	return reservationRepository.findById(id);
    }
    
	public boolean checkIfAvailable(Apartment apartment, LocalDate wantedCheckIn, LocalDate wantedCheckOut) {
		List<Reservation> allReservations = reservationRepository.findAll();
		Boolean check = true;
		
		for (int i = 0; i < allReservations.size(); i++) {
			if (allReservations.get(i).getApartment() == apartment) {
				LocalDate checkIn = allReservations.get(i).getCheckInDate();
				LocalDate checkOut = allReservations.get(i).getCheckOutDate();
				
				if(wantedCheckIn.isBefore(checkIn) && wantedCheckOut.isAfter(checkIn) ||
						wantedCheckIn.isBefore(checkOut) && wantedCheckOut.isAfter(checkOut) ||
						wantedCheckIn.isBefore(checkIn) && wantedCheckOut.isAfter(checkOut) ||
						wantedCheckIn.isAfter(checkIn) && wantedCheckOut.isBefore(checkOut) ||
						wantedCheckIn.equals(checkIn) || wantedCheckOut.equals(checkOut)
						)
					check = false;

				if(allReservations.get(i).getBooked().equals(false))
					check = true;
				
			}
		}
		return check;
	}
	
	public User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserEmail = authentication.getName();
		    User currentUser = userRepository.findByEmail(currentUserEmail);
		    return currentUser;
		}
		return null;
	}
	
	public boolean checkIfToday(Reservation reservation, Map<Reservation, String> todaysReservations) {
		Boolean check = true;
		LocalDate todayDate = LocalDate.now();
		
		LocalDate checkInDate = reservation.getCheckInDate();
		LocalDate checkOutDate = reservation.getCheckOutDate();
		
		if (checkInDate.equals(todayDate) && reservation.ifBooked().equals(true))
			todaysReservations.put(reservation, "Arrival");
		else if (checkInDate.isBefore(todayDate) && checkOutDate.isAfter(todayDate) && reservation.ifBooked().equals(true))
			todaysReservations.put(reservation, "Stayover");
		else if (checkInDate.equals(todayDate) && reservation.ifBooked().equals(false))
			todaysReservations.put(reservation, "Cancelled");
		else if (checkOutDate.equals(todayDate))
			todaysReservations.put(reservation, "Due Out");
		else
			check = false;
	
		return check;
	}
	
	public static Double round(Double value, int places) {
		if (value.isInfinite() || value.isNaN() || value.equals(0.0) || 
				value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY)
			return 0.0;
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
