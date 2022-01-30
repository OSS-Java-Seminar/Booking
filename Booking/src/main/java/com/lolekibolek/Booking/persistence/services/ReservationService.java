package com.lolekibolek.Booking.persistence.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
    
    public Reservation findById(int id) {
    	return reservationRepository.findById(id);
    }
    
	public boolean checkIfAvailable(Apartment apartment, Date wantedCheckIn, Date wantedCheckOut) {
		List<Reservation> allReservations = reservationRepository.findAll();
		Boolean check = true;
		
		for (int i = 0; i < allReservations.size(); i++) {
			if (allReservations.get(i).getApartment() == apartment) {
				Date checkIn = allReservations.get(i).getCheckInDate();
				Date checkOut = allReservations.get(i).getCheckOutDate();
				
				if(wantedCheckIn.before(checkIn) && wantedCheckOut.after(checkIn) ||
						wantedCheckIn.before(checkOut) && wantedCheckOut.after(checkOut) ||
						wantedCheckIn.before(checkIn) && wantedCheckOut.after(checkOut) ||
						wantedCheckIn.after(checkIn) && wantedCheckOut.before(checkOut) ||
						wantedCheckIn.equals(checkIn) || wantedCheckOut.equals(checkOut)
						)
					check = false;

				if(allReservations.get(i).getBooked().equals(false))
					check = true;
				
			}
		}
		return check;
	}
	
	public String getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    return currentUserName;
		}
		return "Guest";
	}
	
	public boolean checkIfToday(Reservation reservation, Map<Reservation, String> todaysReservations) {
		Boolean check = true;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		Date todayDate = null;
		try {
			todayDate = dateFormatter.parse(dateFormatter.format(new Date() ));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date checkInDate = reservation.getCheckInDate();
		Date checkOutDate = reservation.getCheckOutDate();
		
		if (checkInDate.equals(todayDate) && reservation.ifBooked().equals(true))
			todaysReservations.put(reservation, "Arrival");
		else if (checkInDate.before(todayDate) && checkOutDate.after(new Date()) && reservation.ifBooked().equals(true))
			todaysReservations.put(reservation, "Stayover");
		else if (checkInDate.equals(todayDate) && reservation.ifBooked().equals(false))
			todaysReservations.put(reservation, "Cancelled");
		else if (checkOutDate.equals(todayDate))
			todaysReservations.put(reservation, "Due Out");
		else
			check = false;
	
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

	
	
}
